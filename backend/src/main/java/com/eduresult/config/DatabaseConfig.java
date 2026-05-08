package com.eduresult.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

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
        String url = null;
        String finalUrl = "";
        
        // Try SPRING_DATASOURCE_URL first, then DATABASE_URL
        String rawUrl = (springDbUrl != null && !springDbUrl.isEmpty()) ? springDbUrl : databaseUrl;
        
        if (rawUrl != null && !rawUrl.isEmpty()) {
            if (rawUrl.startsWith("postgres://")) {
                finalUrl = "jdbc:postgresql://" + rawUrl.substring(11);
            } else if (rawUrl.startsWith("jdbc:")) {
                finalUrl = rawUrl;
            } else {
                finalUrl = "jdbc:postgresql://" + rawUrl;
            }
        } 
        else if (dbHost != null && !dbHost.isEmpty() && !dbHost.contains("${")) {
            finalUrl = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
        } 
        else {
            finalUrl = "jdbc:sqlite:eduresult.db";
        }

        System.out.println("DEBUG: Raw DB URL found: " + (rawUrl != null ? "YES" : "NO"));
        System.out.println("DEBUG: Using Database URL: " + finalUrl.split("@")[finalUrl.split("@").length - 1]);

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
