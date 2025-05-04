// package com.hacof.hackathon.service;
//
// import com.hacof.identity.dto.ApiResponse;
// import com.hacof.identity.dto.response.UserResponse;
// import org.springframework.cloud.openfeign.FeignClient;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestHeader;
//
// import java.util.List;
//
// @FeignClient(name = "identity-service", path = "/api/v1/users")
// public interface IdentityClientService {
//    @GetMapping
//    ApiResponse<List<UserResponse>> getUsers(@RequestHeader("Authorization") String token);
//
//    @GetMapping("/{userId}")
//    ApiResponse<UserResponse> getUserById(@RequestHeader("Authorization") String token, @PathVariable String userId);
//
//    @GetMapping("/username/{username}")
//    ApiResponse<UserResponse> getUserByUsername(@RequestHeader("Authorization") String token, @PathVariable String
// username);
//
//    @GetMapping("/users-by-roles")
//    ApiResponse<List<UserResponse>> getUsersByRoles(@RequestHeader("Authorization") String token);
//
//    @GetMapping("/team-members")
//    ApiResponse<List<UserResponse>> getTeamMembers(@RequestHeader("Authorization") String token);
//
//    @GetMapping("/users-by-created-by/{createdByUserName}")
//    ApiResponse<List<UserResponse>> getUsersByCreatedByUserName(@RequestHeader("Authorization") String token,
// @PathVariable String createdByUserName);
// }
