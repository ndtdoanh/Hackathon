package com.hacof.identity.controller;

import java.io.IOException;
import java.util.List;

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
                .data(userDeviceResponse)
                .message("UserDevice created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ApiResponse<List<UserDeviceResponse>> getUserDevices() {
        return ApiResponse.<List<UserDeviceResponse>>builder()
                .data(userDeviceService.getUserDevices())
                .message("Get all UserDevices")
                .build();
    }

    @GetMapping("/{Id}")
    public ApiResponse<UserDeviceResponse> getUserDevice(@PathVariable("Id") Long id) {
        return ApiResponse.<UserDeviceResponse>builder()
                .data(userDeviceService.getUserDevice(id))
                .message("Get UserDevice by ID")
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
                .data(updatedUserDevice)
                .message("UserDevice updated successfully")
                .build());
    }

    @DeleteMapping("/{Id}")
        @PreAuthorize("hasAuthority('DELETE_USER_DEVICE')")
    public ApiResponse<Void> deleteUserDevice(@PathVariable("Id") Long id) {
        userDeviceService.deleteUserDevice(id);
        return ApiResponse.<Void>builder()
                .message("UserDevice deleted successfully")
                .build();
    }
}
