package com.hacof.hackathon.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hacof.hackathon.entity.User;

@FeignClient(name = "identity-service")
public interface IdentityClient {
    @GetMapping("/users/{id}")
    User getUserById(@PathVariable("id") Long id);
}
