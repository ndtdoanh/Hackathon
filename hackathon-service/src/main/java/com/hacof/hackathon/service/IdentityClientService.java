package com.hacof.hackathon.service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "identity-service")
public interface IdentityClientService {
    //    @GetMapping("/api/v1/users")
    //    ApiResponse<List<UserResponse>> getUsers(@RequestHeader("Authorization") String token);
}
