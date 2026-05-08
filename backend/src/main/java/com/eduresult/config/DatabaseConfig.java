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
        String url;
        
        // Handle Render's postgres:// format
        if (databaseUrl != null && databaseUrl.startsWith("postgres://")) {
            url = "jdbc:postgresql://" + databaseUrl.substring(11);
        } 
        // Handle individual components if provided
        else if (dbHost != null && !dbHost.isEmpty() && !dbHost.contains("${")) {
            url = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
        } 
        // Fallback to SQLite for local development
        else {
            url = "jdbc:sqlite:eduresult.db";
        }

        System.out.println("Connecting to database with URL: " + url.split("@")[url.split("@").length - 1]); // Log safely

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(url);
        
        if (username != null && !username.isEmpty() && !username.contains("${")) {
            dataSourceBuilder.username(username);
        }
        
        if (password != null && !password.isEmpty() && !password.contains("${")) {
            dataSourceBuilder.password(password);
        }
        
        // Set driver class name based on URL
        if (url.startsWith("jdbc:postgresql:")) {
            dataSourceBuilder.driverClassName("org.postgresql.Driver");
        } else {
            dataSourceBuilder.driverClassName("org.xerial.sqlite.JDBC");
        }

        return dataSourceBuilder.build();
    }
}
