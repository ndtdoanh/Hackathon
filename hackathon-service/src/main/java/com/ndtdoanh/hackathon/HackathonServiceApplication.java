package com.ndtdoanh.hackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HackathonServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HackathonServiceApplication.class, args);
    }
}
