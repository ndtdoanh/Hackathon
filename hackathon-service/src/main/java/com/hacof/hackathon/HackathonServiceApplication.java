package com.hacof.hackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing

// @EnableDiscoveryClient
public class HackathonServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HackathonServiceApplication.class, args);
    }
}
