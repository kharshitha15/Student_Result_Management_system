package com.eduresult.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Value("${SPRING_DATASOURCE_URL:}")
    private String springDbUrl;

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @Value("${DB_HOST:}")
    private String dbHost;

    @Value("${DB_PORT:5432}")
    private String dbPort;

    @Value("${DB_NAME:}")
    private String dbName;

    @Value("${SPRING_DATASOURCE_USERNAME:}")
    private String username;

    @Value("${SPRING_DATASOURCE_PASSWORD:}")
    private String password;

    @Bean
    @Primary
    public DataSource dataSource() {
        // Log all environment variables for debugging (safely)
        logger.info("--- ENVIRONMENT VARIABLES START ---");
        System.getenv().forEach((k, v) -> {
            if (k.contains("URL") || k.contains("PASSWORD") || k.contains("SECRET") || k.contains("KEY")) {
                logger.info("{}: [REDACTED] (Length: {})", k, (v != null ? v.length() : 0));
            } else {
                logger.info("{}: {}", k, v);
            }
        });
        logger.info("--- ENVIRONMENT VARIABLES END ---");

        String finalUrl = "";
        
        // Try SPRING_DATASOURCE_URL first, then DATABASE_URL
        String rawUrl = (springDbUrl != null && !springDbUrl.isEmpty()) ? springDbUrl : databaseUrl;
        
        boolean isRender = System.getenv("RENDER") != null;

        if (rawUrl != null && !rawUrl.isEmpty()) {
            logger.info("DEBUG: Found raw URL in environment");
            if (rawUrl.startsWith("postgres://")) {
                finalUrl = "jdbc:postgresql://" + rawUrl.substring(11);
            } else if (rawUrl.startsWith("jdbc:")) {
                finalUrl = rawUrl;
            } else {
                finalUrl = "jdbc:postgresql://" + rawUrl;
            }
        } 
        else if (dbHost != null && !dbHost.isEmpty() && !dbHost.contains("${")) {
            logger.info("DEBUG: Found DB_HOST in environment");
            finalUrl = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
        } 
        else if (isRender) {
            logger.warn("DEBUG: Running on Render but no URL found! Defaulting to local postgres for connection check...");
            finalUrl = "jdbc:postgresql://localhost:5432/eduresult";
        }
        else {
            logger.info("DEBUG: Defaulting to SQLite");
            finalUrl = "jdbc:sqlite:eduresult.db";
        }

        logger.info("DEBUG: Using Database URL: {}", finalUrl.split("@")[finalUrl.split("@").length - 1]);

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(finalUrl);
        
        if (username != null && !username.isEmpty() && !username.contains("${")) {
            dataSourceBuilder.username(username);
        }
        
        if (password != null && !password.isEmpty() && !password.contains("${")) {
            dataSourceBuilder.password(password);
        }
        
        if (finalUrl.startsWith("jdbc:postgresql:")) {
            dataSourceBuilder.driverClassName("org.postgresql.Driver");
        } else {
            dataSourceBuilder.driverClassName("org.xerial.sqlite.JDBC");
        }

        return dataSourceBuilder.build();
    }
}
