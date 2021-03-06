package com.example.cache.x;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CachexApplication {

    public static void main(String[] args) {
        SpringApplication.run(CachexApplication.class, args);
    }



}
