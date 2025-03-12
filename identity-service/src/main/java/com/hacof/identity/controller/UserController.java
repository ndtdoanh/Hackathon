package com.hacof.identity.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.request.AddEmailRequest;
import com.hacof.identity.dto.request.PasswordCreateRequest;
import com.hacof.identity.dto.request.UserCreateRequest;
import com.hacof.identity.dto.request.UserUpdateRequest;
import com.hacof.identity.dto.request.VerifyEmailRequest;
import com.hacof.identity.dto.response.UserResponse;
import com.hacof.identity.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_USER')")
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
    @PreAuthorize("hasAuthority('CREATE_PASSWORD')")
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
    @PreAuthorize("hasAuthority('GET_MY_INFO')")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .message("Get my-info")
                .build();
    }

    @PutMapping("/my-info")
    @PreAuthorize("hasAuthority('UPDATE_MY_INFO')")
    public ApiResponse<UserResponse> updateUser(@RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateMyInfo(request))
                .message("User updated successfully")
                .build();
    }

    @DeleteMapping("/{Id}")
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public ApiResponse<String> deleteUser(@PathVariable("Id") long userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

    @PostMapping("/add-email")
    @PreAuthorize("hasAuthority('ADD_EMAIL')")
    public ResponseEntity<ApiResponse<String>> addEmail(
            @Valid @RequestBody AddEmailRequest request, @AuthenticationPrincipal Jwt jwt) {
        try {
            Long userId = jwt.getClaim("user_id");

            String message = userService.addEmail(userId, request.getEmail());

            log.info("User {} requested to add email: {}", userId, request.getEmail());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.<String>builder().message(message).build());
        } catch (IllegalArgumentException e) {
            log.warn("Invalid email request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<String>builder().message(e.getMessage()).build());
        } catch (Exception e) {
            log.error("Error processing add email request: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<String>builder()
                            .message("Error occurred when processing requests")
                            .build());
        }
    }

    @PostMapping("/verify-email")
    @PreAuthorize("hasAuthority('VERIFY_EMAIL')")
    public ResponseEntity<ApiResponse<String>> verifyEmail(
            @Valid @RequestBody VerifyEmailRequest request, @AuthenticationPrincipal Jwt jwt) {
        try {
            Long userId = jwt.getClaim("user_id");
            String message = userService.verifyEmail(userId, request.getOtp());

            log.info("User {} successfully verified email", userId);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.<String>builder().message(message).build());
        } catch (IllegalArgumentException e) {
            log.warn("Invalid OTP verification: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<String>builder().message(e.getMessage()).build());
        } catch (IllegalStateException e) {
            log.warn("Email verification state error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<String>builder().message(e.getMessage()).build());
        } catch (Exception e) {
            log.error("Error processing verify email request: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<String>builder()
                            .message("Error occurred when processing requests")
                            .build());
        }
    }
}
