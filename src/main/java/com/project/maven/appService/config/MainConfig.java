package com.project.maven.appService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.DriverManager;

@Configuration
public class MainConfig {

    @Bean
    public DataSource getDataSource(){
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName("com.mysql.cj.jdbc.Driver");
        driver.setUrl("jdbc:mysql://192.168.100.250/bmt_v1?serverTimezone=UTC");
        driver.setUsername("root");
        driver.setPassword("passwordnyaRoot");

        return  driver;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(getDataSource());
    }
}

