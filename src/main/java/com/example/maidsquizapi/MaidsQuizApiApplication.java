package com.example.maidsquizapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MaidsQuizApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaidsQuizApiApplication.class, args);
    }

}
