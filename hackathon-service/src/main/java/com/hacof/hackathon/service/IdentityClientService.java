package com.hacof.hackathon.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "identity-service")
public interface IdentityClientService {
//    @GetMapping("/api/v1/users")
//    ApiResponse<List<UserResponse>> getUsers(@RequestHeader("Authorization") String token);
}
