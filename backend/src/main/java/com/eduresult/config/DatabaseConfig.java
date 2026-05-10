package com.eduresult.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class DatabaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Value("${SPRING_DATASOURCE_URL:}")
    private String springDbUrl;

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @Value("${INTERNAL_DATABASE_URL:}")
    private String internalDbUrl;

    @Value("${DB_HOST:}")
    private String dbHost;

    @Value("${DB_PORT:5432}")
    private String dbPort;

    @Value("${DB_NAME:}")
    private String dbName;

    @Value("${SPRING_DATASOURCE_USERNAME:}")
    private String envUsername;

    @Value("${SPRING_DATASOURCE_PASSWORD:}")
    private String envPassword;

    @Bean
    @Primary
    public DataSource dataSource() {
        System.out.println("**************************************************");
        System.out.println("DATABASE_DEBUG_CHECK: Starting DataSource Configuration");
        System.out.println("**************************************************");
        
        String finalUrl = "";
        String finalUsername = envUsername;
        String finalPassword = envPassword;

        // Try all possible environment variables for the URL
        String rawUrl = springDbUrl;
        if (rawUrl == null || rawUrl.isEmpty()) rawUrl = databaseUrl;
        if (rawUrl == null || rawUrl.isEmpty()) rawUrl = internalDbUrl;
        if (rawUrl == null || rawUrl.isEmpty()) rawUrl = System.getenv("JDBC_DATABASE_URL");
        if (rawUrl == null || rawUrl.isEmpty()) rawUrl = System.getenv("DATABASE_URL");

        boolean isRender = System.getenv("RENDER") != null || System.getenv("RENDER_SERVICE_ID") != null;
        
        logger.info("DEBUG: Environment Check - isRender: {}, rawUrl present: {}", isRender, (rawUrl != null && !rawUrl.isEmpty()));
        if (rawUrl != null && !rawUrl.isEmpty()) {
            System.out.println("DATABASE_DEBUG_CHECK: Found raw URL: " + rawUrl.split("@")[rawUrl.split("@").length - 1]);
            try {
                // Clean the protocol for URI parsing if needed
                String uriString = rawUrl;
                if (uriString.startsWith("jdbc:postgresql://")) {
                    uriString = uriString.substring(5); // Remove jdbc:
                } else if (uriString.startsWith("postgresql://")) {
                    // already fine
                } else if (uriString.startsWith("postgres://")) {
                    uriString = "postgresql://" + uriString.substring(11);
                }

                URI dbUri = new URI(uriString);
                
                // Extract credentials from URI if present
                if (dbUri.getUserInfo() != null) {
                    String[] userInfo = dbUri.getUserInfo().split(":");
                    finalUsername = userInfo[0];
                    if (userInfo.length > 1) {
                        finalPassword = userInfo[1];
                    }
                }

                // Construct clean JDBC URL (no credentials in the string)
                String host = dbUri.getHost();
                int port = dbUri.getPort();
                String path = dbUri.getPath();
                
                if (port == -1) port = 5432;
                
                finalUrl = "jdbc:postgresql://" + host + ":" + port + path;
                
            } catch (Exception e) {
                logger.warn("DEBUG: URI parsing failed, falling back to manual string manipulation: {}", e.getMessage());
                // Fallback for non-standard URLs
                if (rawUrl.contains("@")) {
                    // Extract user:pass@host
                    String withoutProtocol = rawUrl.contains("://") ? rawUrl.split("://")[1] : rawUrl;
                    String credentials = withoutProtocol.split("@")[0];
                    String hostPart = withoutProtocol.split("@")[1];
                    
                    if (credentials.contains(":")) {
                        finalUsername = credentials.split(":")[0];
                        finalPassword = credentials.split(":")[1];
                    }
                    finalUrl = "jdbc:postgresql://" + hostPart;
                } else {
                    finalUrl = rawUrl.startsWith("jdbc:") ? rawUrl : "jdbc:postgresql://" + rawUrl;
                }
            }
        } 
        else if (dbHost != null && !dbHost.isEmpty() && !dbHost.contains("${")) {
            finalUrl = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
        } 
        else if (isRender) {
            // Last resort for Render
            finalUrl = "jdbc:postgresql://localhost:5432/eduresult";
        }
        else {
            finalUrl = "jdbc:sqlite:eduresult.db";
        }

        // Final URL Sanity Check (Remove double prefixes if any)
        if (finalUrl.startsWith("jdbc:postgresql://postgresql://")) {
            finalUrl = "jdbc:postgresql://" + finalUrl.substring(31);
        }

        logger.info("DEBUG: Final JDBC URL: {}", finalUrl);

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(finalUrl);
        dataSourceBuilder.username(finalUsername);
        dataSourceBuilder.password(finalPassword);
        
        if (finalUrl.startsWith("jdbc:postgresql:")) {
            dataSourceBuilder.driverClassName("org.postgresql.Driver");
        } else {
            dataSourceBuilder.driverClassName("org.xerial.sqlite.JDBC");
        }

        return dataSourceBuilder.build();
    }
}
