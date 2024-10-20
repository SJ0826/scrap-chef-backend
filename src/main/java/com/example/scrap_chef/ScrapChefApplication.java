package com.example.scrap_chef;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class ScrapChefApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrapChefApplication.class, args);
    }

    @Bean
    CommandLineRunner testDatabaseConnection(DataSource dataSource) {
        return args -> {
            try {
                dataSource.getConnection().close();
                System.out.println("데이터베이스 연결 성공!");
            } catch (Exception e) {
                System.out.println("데이터베이스 연결 실패: " + e);
            }
        };
    }
}
