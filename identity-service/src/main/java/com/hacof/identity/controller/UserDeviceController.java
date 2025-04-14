package com.hacof.identity.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.hacof.identity.dto.response.FileUrlResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.request.UserDeviceRequest;
import com.hacof.identity.dto.response.UserDeviceResponse;
import com.hacof.identity.service.UserDeviceService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/user-devices")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDeviceController {
    UserDeviceService userDeviceService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_USER_DEVICE')")
    public ResponseEntity<ApiResponse<UserDeviceResponse>> createUserDevice(
            @ModelAttribute @Valid UserDeviceRequest request,
            @RequestParam(value = "files", required = false) List<MultipartFile> files)
            throws IOException {

        UserDeviceResponse userDeviceResponse = userDeviceService.createUserDevice(request, files);

        ApiResponse<UserDeviceResponse> response = ApiResponse.<UserDeviceResponse>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userDeviceResponse)
                .message("UserDevice created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ApiResponse<List<UserDeviceResponse>> getUserDevices() {
        return ApiResponse.<List<UserDeviceResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userDeviceService.getUserDevices())
                .message("Get all UserDevices")
                .build();
    }

    @GetMapping("/{Id}")
    public ApiResponse<UserDeviceResponse> getUserDevice(@PathVariable("Id") Long id) {
        return ApiResponse.<UserDeviceResponse>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userDeviceService.getUserDevice(id))
                .message("Get UserDevice by ID")
                .build();
    }

    @GetMapping("/device/{deviceId}")
    public ApiResponse<List<UserDeviceResponse>> getUserDevicesByDeviceId(@PathVariable String deviceId) {
        return ApiResponse.<List<UserDeviceResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userDeviceService.getUserDevicesByDeviceId(deviceId))
                .message("Get UserDevices by deviceId")
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<UserDeviceResponse>> getUserDevicesByUserId(@PathVariable String userId) {
        return ApiResponse.<List<UserDeviceResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userDeviceService.getUserDevicesByUserId(userId))
                .message("Get UserDevices by userId")
                .build();
    }

    @GetMapping("/{userDeviceId}/file-urls")
    public ApiResponse<List<FileUrlResponse>> getFileUrlsByUserDeviceId(@PathVariable Long userDeviceId) {
        return ApiResponse.<List<FileUrlResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userDeviceService.getFileUrlsByUserDeviceId(userDeviceId))
                .message("Get file URLs by userDeviceId")
                .build();
    }

    @PutMapping("/{Id}")
    @PreAuthorize("hasAuthority('UPDATE_USER_DEVICE')")
    public ResponseEntity<ApiResponse<UserDeviceResponse>> updateUserDevice(
            @PathVariable("Id") Long id,
            @ModelAttribute UserDeviceRequest request,
            @RequestParam(value = "files", required = false) List<MultipartFile> files)
            throws IOException {

        UserDeviceResponse updatedUserDevice = userDeviceService.updateUserDevice(id, request, files);

        return ResponseEntity.ok(ApiResponse.<UserDeviceResponse>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(updatedUserDevice)
                .message("UserDevice updated successfully")
                .build());
    }

    @DeleteMapping("/{Id}")
    @PreAuthorize("hasAuthority('DELETE_USER_DEVICE')")
    public ApiResponse<Void> deleteUserDevice(@PathVariable("Id") Long id) {
        userDeviceService.deleteUserDevice(id);
        return ApiResponse.<Void>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .message("UserDevice deleted successfully")
                .build();
    }
}
