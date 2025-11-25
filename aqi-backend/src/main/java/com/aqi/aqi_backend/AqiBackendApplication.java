package com.aqi.aqi_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.aqi")

public class AqiBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(AqiBackendApplication.class, args);
    }
}
