package com.hacof.identity.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.identity.dtos.request.ApiResponse;
import com.hacof.identity.dtos.request.UserUpdateRequest;
import com.hacof.identity.dtos.response.UserResponse;
import com.hacof.identity.services.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    //    @PostMapping
    //    public ResponseEntity<ApiResponse<UserResponse>> createUser(
    //            @RequestBody @Valid UserCreateRequest request, @RequestHeader("Authorization") String
    // authorizationHeader) {
    //        String jwtToken = authorizationHeader.replace("Bearer ", "");
    //        UserResponse userResponse = userService.createUser(request, jwtToken);
    //
    //        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
    //                .result(userResponse)
    //                .build();
    //
    //        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    //    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{Id}")
    public ApiResponse<UserResponse> getUser(@PathVariable("Id") long userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{Id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable("Id") long userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("/{Id}")
    public ApiResponse<String> deleteUser(@PathVariable("Id") long userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }
}
