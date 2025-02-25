package com.expense.tracker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${db.server}")
    private String databaseServer;

    @Value("${db.name}")
    private String databaseName;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.driver}")
    private String driver;

    @Bean
    public DataSource dataSource() {
        StringBuilder url = new StringBuilder();
        url.append("jdbc:mysql")
                .append("://")
                .append(databaseServer)
                .append("/")
                .append(databaseName);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url.toString());
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
