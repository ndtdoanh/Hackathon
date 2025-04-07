package com.hacof.identity.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.request.UserDeviceTrackRequest;
import com.hacof.identity.dto.response.UserDeviceTrackResponse;
import com.hacof.identity.service.UserDeviceTrackService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/user-device-tracks")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDeviceTrackController {
    UserDeviceTrackService userDeviceTrackService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_USER_DEVICE_TRACK')")
    public ResponseEntity<ApiResponse<UserDeviceTrackResponse>> createUserDeviceTrack(
            @ModelAttribute @Valid UserDeviceTrackRequest request,
            @RequestParam(value = "files", required = false) List<MultipartFile> files)
            throws Exception {

        UserDeviceTrackResponse userDeviceTrackResponse = userDeviceTrackService.createUserDeviceTrack(request, files);

        ApiResponse<UserDeviceTrackResponse> response = ApiResponse.<UserDeviceTrackResponse>builder()
                .data(userDeviceTrackResponse)
                .message("UserDeviceTrack created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ApiResponse<List<UserDeviceTrackResponse>> getUserDeviceTracks() {
        return ApiResponse.<List<UserDeviceTrackResponse>>builder()
                .data(userDeviceTrackService.getUserDeviceTracks())
                .message("Get all device tracks")
                .build();
    }

    @GetMapping("/{Id}")
    public ApiResponse<UserDeviceTrackResponse> getUserDeviceTrackById(@PathVariable("Id") Long id) {
        return ApiResponse.<UserDeviceTrackResponse>builder()
                .data(userDeviceTrackService.getUserDeviceTrack(id))
                .message("Get device track by id")
                .build();
    }

    @PutMapping("/{Id}")
    @PreAuthorize("hasAuthority('UPDATE_USER_DEVICE_TRACK')")
    public ResponseEntity<ApiResponse<UserDeviceTrackResponse>> updateUserDeviceTrack(
            @PathVariable("Id") Long id,
            @ModelAttribute @Valid UserDeviceTrackRequest request,
            @RequestParam(value = "files", required = false) List<MultipartFile> files)
            throws Exception {

        UserDeviceTrackResponse userDeviceTrackResponse =
                userDeviceTrackService.updateUserDeviceTrack(id, request, files);

        return ResponseEntity.ok(ApiResponse.<UserDeviceTrackResponse>builder()
                .data(userDeviceTrackResponse)
                .message("UserDeviceTrack updated successfully")
                .build());
    }

    @DeleteMapping("/{Id}")
    @PreAuthorize("hasAuthority('DELETE_USER_DEVICE_TRACK')")
    public ApiResponse<Void> deleteUserDeviceTrack(@PathVariable("Id") Long id) {
        userDeviceTrackService.deleteUserDeviceTrack(id);
        return ApiResponse.<Void>builder()
                .message("UserDeviceTrack deleted successfully")
                .build();
    }
}
