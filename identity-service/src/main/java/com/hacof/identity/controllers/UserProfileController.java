package com.hacof.identity.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.dtos.ApiResponse;
import com.hacof.identity.dtos.request.UserProfileCreateRequest;
import com.hacof.identity.dtos.request.UserProfileUpdateRequest;
import com.hacof.identity.dtos.response.UserProfileResponse;
import com.hacof.identity.services.UserProfileService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    UserProfileService userProfileService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_PROFILE')")
    public ResponseEntity<ApiResponse<UserProfileResponse>> createProfile(
            @Valid @RequestBody UserProfileCreateRequest request) {
        UserProfileResponse profileResponse = userProfileService.createProfile(request);
        ApiResponse<UserProfileResponse> response = ApiResponse.<UserProfileResponse>builder()
                .result(profileResponse)
                .message("Profile created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_PROFILE')")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateProfile(
            @Valid @RequestBody UserProfileUpdateRequest request) {
        UserProfileResponse profileResponse = userProfileService.updateProfile(request);
        ApiResponse<UserProfileResponse> response = ApiResponse.<UserProfileResponse>builder()
                .result(profileResponse)
                .message("Profile updated successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{Id}")
    @PreAuthorize("hasAuthority('GET_PROFILE')")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile(@PathVariable Long Id) {
        UserProfileResponse profileResponse = userProfileService.getProfile(Id);
        ApiResponse<UserProfileResponse> response = ApiResponse.<UserProfileResponse>builder()
                .result(profileResponse)
                .message("Profile retrieved successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GET_PROFILES')")
    public ResponseEntity<ApiResponse<List<UserProfileResponse>>> getProfiles() {
        List<UserProfileResponse> profiles = userProfileService.getProfiles();
        ApiResponse<List<UserProfileResponse>> response = ApiResponse.<List<UserProfileResponse>>builder()
                .result(profiles)
                .message("All profiles retrieved successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('DELETE_PROFILE')")
    public ResponseEntity<ApiResponse<Void>> deleteProfile() {
        userProfileService.deleteProfile();
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Profile deleted successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/avatar")
    @PreAuthorize("hasAuthority('UPLOAD_AVATAR')")
    public ResponseEntity<ApiResponse<UserProfileResponse>> uploadAvatar(@RequestParam("file") MultipartFile file) {
        UserProfileResponse profileResponse = userProfileService.uploadAvatar(file);
        ApiResponse<UserProfileResponse> response = ApiResponse.<UserProfileResponse>builder()
                .result(profileResponse)
                .message("Avatar uploaded successfully")
                .build();

        return ResponseEntity.ok(response);
    }
}
