package com.hacof.identity.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.hacof.identity.constant.Status;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.dto.ApiRequest;
import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.request.AddEmailRequest;
import com.hacof.identity.dto.request.ChangePasswordRequest;
import com.hacof.identity.dto.request.ForgotPasswordRequest;
import com.hacof.identity.dto.request.OrganizerUpdateForJudgeMentor;
import com.hacof.identity.dto.request.PasswordCreateRequest;
import com.hacof.identity.dto.request.ResetPasswordRequest;
import com.hacof.identity.dto.request.UserCreateRequest;
import com.hacof.identity.dto.request.UserUpdateRequest;
import com.hacof.identity.dto.request.VerifyEmailRequest;
import com.hacof.identity.dto.response.AvatarResponse;
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
    public ResponseEntity<ApiResponse> createUser(
            @RequestHeader("Authorization") String authorizationToken,
            @Valid @RequestBody ApiRequest<UserCreateRequest> request) {

        String token = authorizationToken.replace("Bearer ", "");

        UserResponse userResponse = userService.createUser(token, request.getData());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<UserResponse>builder()
                        .requestId(request.getRequestId())
                        .requestDateTime(request.getRequestDateTime())
                        .channel(request.getChannel())
                        .message("User created successfully")
                        .data(userResponse)
                        .build());
    }

    @PostMapping("/create-password")
    public ResponseEntity<ApiResponse<Void>> createPassword(
            @RequestBody @Valid ApiRequest<PasswordCreateRequest> request) {
        userService.createPassword(request.getData());

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .requestId(request.getRequestId())
                .requestDateTime(request.getRequestDateTime())
                .channel(request.getChannel())
                .message("Password has been created, you could use it to log-in")
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userService.getUsers())
                .message("Get all users")
                .build();
    }

    @GetMapping("/{Id}")
    public ApiResponse<UserResponse> getUser(@PathVariable("Id") long userId) {
        return ApiResponse.<UserResponse>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userService.getUserById(userId))
                .message("Get user by Id")
                .build();
    }

    @GetMapping("username/{username}")
    public ApiResponse<UserResponse> getUser(@PathVariable String username) {
        return ApiResponse.<UserResponse>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userService.getUserByUserName(username))
                .message("Get user by Username")
                .build();
    }

    @GetMapping("/users-by-roles")
    public ApiResponse<List<UserResponse>> getUsersByRoles() {
        return ApiResponse.<List<UserResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userService.getUsersByRoles())
                .message("Get users by roles: ORGANIZER, JUDGE, MENTOR")
                .build();
    }

    @GetMapping("/team-members")
    public ApiResponse<List<UserResponse>> getTeamMembers() {
        return ApiResponse.<List<UserResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userService.getTeamMembers())
                .message("Get team members")
                .build();
    }

    @GetMapping("/users-by-created-by/{createdByUserName}")
    public ApiResponse<List<UserResponse>> getUsersByCreatedByUserName(@PathVariable String createdByUserName) {
        return ApiResponse.<List<UserResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userService.getUsersByCreatedByUserName(createdByUserName))
                .message("Get users created by: " + createdByUserName)
                .build();
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userService.getMyInfo())
                .message("Get my-info")
                .build();
    }

    @PutMapping("/my-info")
    public ApiResponse<UserResponse> updateMyInfo(@Valid @RequestBody ApiRequest<UserUpdateRequest> request) {
        return ApiResponse.<UserResponse>builder()
                .requestId(request.getRequestId())
                .requestDateTime(request.getRequestDateTime())
                .channel(request.getChannel())
                .data(userService.updateMyInfo(request.getData()))
                .message("User updated successfully")
                .build();
    }

    @PutMapping("/organizer/{userId}")
    @PreAuthorize("hasAuthority('UPDATE_JUDGE_MENTOR_BY_ORGANIZER')")
    public ApiResponse<UserResponse> updateJudgeMentorByOrganizer(
            @PathVariable Long userId, @Valid @RequestBody ApiRequest<OrganizerUpdateForJudgeMentor> request) {

        return ApiResponse.<UserResponse>builder()
                .requestId(request.getRequestId())
                .requestDateTime(request.getRequestDateTime())
                .channel(request.getChannel())
                .data(userService.updateJudgeMentorByOrganization(userId, request.getData()))
                .message("Updated Judge or Mentor by Organizer successfully")
                .build();
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('UPDATE_USER_STATUS')")
    public ApiResponse<String> updateUserStatus(@PathVariable("id") long userId, @RequestParam Status status) {
        userService.toggleUserStatus(userId, status);
        return ApiResponse.<String>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data("User status has been updated")
                .build();
    }

    @PostMapping("/add-email")
    public ResponseEntity<ApiResponse<String>> addEmail(
            @Valid @RequestBody ApiRequest<AddEmailRequest> request, @AuthenticationPrincipal Jwt jwt) {
        try {

            String message = userService.addEmail(request.getData().getEmail());

            log.info("User {} requested to add email: {}", request.getData().getEmail());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.<String>builder()
                            .requestId(request.getRequestId())
                            .requestDateTime(request.getRequestDateTime())
                            .channel(request.getChannel())
                            .message(message)
                            .build());
        } catch (IllegalArgumentException e) {
            log.warn("Invalid email request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<String>builder()
                            .requestId(request.getRequestId())
                            .requestDateTime(request.getRequestDateTime())
                            .channel(request.getChannel())
                            .message(e.getMessage())
                            .build());
        } catch (Exception e) {
            log.error("Error processing add email request: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<String>builder()
                            .requestId(request.getRequestId())
                            .requestDateTime(request.getRequestDateTime())
                            .channel(request.getChannel())
                            .message("Error occurred when processing requests")
                            .build());
        }
    }

    @PostMapping("/verify-email")
    public ResponseEntity<ApiResponse<String>> verifyEmail(
            @Valid @RequestBody ApiRequest<VerifyEmailRequest> request, @AuthenticationPrincipal Jwt jwt) {
        try {
            Long userId = jwt.getClaim("user_id");
            String message = userService.verifyEmail(userId, request.getData().getOtp());

            log.info("User {} successfully verified email", userId);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.<String>builder()
                            .requestId(request.getRequestId())
                            .requestDateTime(request.getRequestDateTime())
                            .channel(request.getChannel())
                            .message(message)
                            .build());
        } catch (IllegalArgumentException e) {
            log.warn("Invalid OTP verification: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<String>builder()
                            .requestId(request.getRequestId())
                            .requestDateTime(request.getRequestDateTime())
                            .channel(request.getChannel())
                            .message(e.getMessage())
                            .build());
        } catch (IllegalStateException e) {
            log.warn("Email verification state error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<String>builder()
                            .requestId(request.getRequestId())
                            .requestDateTime(request.getRequestDateTime())
                            .channel(request.getChannel())
                            .message(e.getMessage())
                            .build());
        } catch (Exception e) {
            log.error("Error processing verify email request: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<String>builder()
                            .requestId(request.getRequestId())
                            .requestDateTime(request.getRequestDateTime())
                            .channel(request.getChannel())
                            .message("Error occurred when processing requests")
                            .build());
        }
    }

    @PostMapping("/change-password")
    public ApiResponse<String> changePassword(@Valid @RequestBody ApiRequest<ChangePasswordRequest> request) {
        return ApiResponse.<String>builder()
                .requestId(request.getRequestId())
                .requestDateTime(request.getRequestDateTime())
                .channel(request.getChannel())
                .message(userService.changePassword(request.getData()))
                .build();
    }

    @PostMapping("/forgot-password")
    public ApiResponse<String> forgotPassword(@RequestBody @Valid ApiRequest<ForgotPasswordRequest> request) {
        return ApiResponse.<String>builder()
                .requestId(request.getRequestId())
                .requestDateTime(request.getRequestDateTime())
                .channel(request.getChannel())
                .message(userService.forgotPassword(request.getData()))
                .build();
    }

    @PostMapping("/reset-password")
    public ApiResponse<String> resetPassword(@Valid @RequestBody ApiRequest<ResetPasswordRequest> request) {
        return ApiResponse.<String>builder()
                .requestId(request.getRequestId())
                .requestDateTime(request.getRequestDateTime())
                .channel(request.getChannel())
                .message(userService.resetPassword(request.getData()))
                .build();
    }

    @PostMapping("/upload-avatar")
    public ResponseEntity<ApiResponse<AvatarResponse>> uploadAvatar(
            @RequestParam("file") MultipartFile file, Authentication authentication) {
        try {
            AvatarResponse avatarResponse = userService.uploadAvatar(file, authentication);
            return ResponseEntity.ok(ApiResponse.<AvatarResponse>builder()
                    .requestId(UUID.randomUUID().toString())
                    .requestDateTime(LocalDateTime.now())
                    .channel("HACOF")
                    .message("Avatar uploaded successfully")
                    .data(avatarResponse)
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.<AvatarResponse>builder()
                            .requestId(UUID.randomUUID().toString())
                            .requestDateTime(LocalDateTime.now())
                            .channel("HACOF")
                            .message(e.getMessage())
                            .build());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<AvatarResponse>builder()
                            .requestId(UUID.randomUUID().toString())
                            .requestDateTime(LocalDateTime.now())
                            .channel("HACOF")
                            .message("Upload failed: " + e.getMessage())
                            .build());
        }
    }
}
