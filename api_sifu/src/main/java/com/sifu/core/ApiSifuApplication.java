package com.sifu.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.sifu.core.utils.entity")
public class ApiSifuApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiSifuApplication.class, args);
    }

}
