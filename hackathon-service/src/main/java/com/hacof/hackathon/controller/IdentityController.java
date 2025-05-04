// package com.hacof.hackathon.controller;
//
// import com.hacof.hackathon.service.IdentityClientService;
// import com.hacof.identity.dto.ApiResponse;
// import com.hacof.identity.dto.response.UserResponse;
// import lombok.RequiredArgsConstructor;
// import lombok.experimental.FieldDefaults;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.web.bind.annotation.*;
//
// import java.util.List;
//
// @RestController
// @RequestMapping("/api/v1/identity")
// @RequiredArgsConstructor
// @Slf4j
// @FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
// public class IdentityController {
//    IdentityClientService identityClientService;
//
//    @GetMapping("/users")
//    public ApiResponse<List<UserResponse>> getUsers(@RequestHeader("Authorization") String token) {
//        return identityClientService.getUsers(token);
//    }
//
//    @GetMapping("/users/{userId}")
//    public ApiResponse<UserResponse> getUserById(@RequestHeader("Authorization") String token, @PathVariable String
// userId) {
//        return identityClientService.getUserById(token, userId);
//    }
//
//    @GetMapping("/users/username/{username}")
//    public ApiResponse<UserResponse> getUserByUsername(@RequestHeader("Authorization") String token, @PathVariable
// String username) {
//        return identityClientService.getUserByUsername(token, username);
//    }
//
//    @GetMapping("/users-by-roles")
//    public ApiResponse<List<UserResponse>> getUsersByRoles(@RequestHeader("Authorization") String token) {
//        return identityClientService.getUsersByRoles(token);
//    }
//
//    @GetMapping("/team-members")
//    public ApiResponse<List<UserResponse>> getTeamMembers(@RequestHeader("Authorization") String token) {
//        return identityClientService.getTeamMembers(token);
//    }
//
//    @GetMapping("/users-by-created-by/{createdByUserName}")
//    public ApiResponse<List<UserResponse>> getUsersByCreatedByUserName(@RequestHeader("Authorization") String token,
// @PathVariable String createdByUserName) {
//        return identityClientService.getUsersByCreatedByUserName(token, createdByUserName);
//    }
// }
