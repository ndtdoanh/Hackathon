package com.hacof.identity.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.identity.dtos.ApiResponse;
import com.hacof.identity.dtos.request.PasswordCreateRequest;
import com.hacof.identity.dtos.request.UserCreateRequest;
import com.hacof.identity.dtos.request.UserUpdateRequest;
import com.hacof.identity.dtos.response.UserResponse;
import com.hacof.identity.services.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(
            @RequestHeader("Authorization") String authorizationToken, @Valid @RequestBody UserCreateRequest request) {

        String token = authorizationToken.replace("Bearer ", "");

        UserResponse userResponse = userService.createUser(token, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<UserResponse>builder()
                        .message("User created successfully")
                        .result(userResponse)
                        .build());
    }

    @PostMapping("/create-password")
    public ResponseEntity<ApiResponse<Void>> createPassword(@RequestBody @Valid PasswordCreateRequest request) {
        userService.createPassword(request);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Password has been created, you could use it to log-in")
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GET_USERS')")
    public ApiResponse<List<UserResponse>> getUsers() {

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .message("Get all users")
                .build();
    }

    @GetMapping("/{Id}")
    @PreAuthorize("hasAuthority('GET_USER')")
    public ApiResponse<UserResponse> getUser(@PathVariable("Id") long userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .message("Get user by Id")
                .build();
    }

    @GetMapping("/my-info")
//    @PreAuthorize("hasAuthority('GET_MY_INFO')")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .message("Get my-info")
                .build();
    }

    @PutMapping("/{Id}")
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable("Id") long userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .message("User updated successfully")
                .build();
    }

    @DeleteMapping("/{Id}")
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public ApiResponse<String> deleteUser(@PathVariable("Id") long userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }
}
