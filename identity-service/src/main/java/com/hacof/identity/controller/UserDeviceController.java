package com.hacof.identity.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.request.AssignDeviceRequest;
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

    @PostMapping("/assign")
    @PreAuthorize("hasAuthority('ASSIGN_DEVICE')")
    public ResponseEntity<ApiResponse<UserDeviceResponse>> assignDevice(
            @RequestBody @Valid AssignDeviceRequest request) {
        UserDeviceResponse response = userDeviceService.assignDevice(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<UserDeviceResponse>builder()
                        .data(response)
                        .message("Device assigned successfully")
                        .build());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GET_USER_DEVICES')")
    public ApiResponse<List<UserDeviceResponse>> getUserDevices() {
        return ApiResponse.<List<UserDeviceResponse>>builder()
                .data(userDeviceService.getUserDevices())
                .message("Get all assigned devices")
                .build();
    }

    @GetMapping("/{Id}")
    @PreAuthorize("hasAuthority('GET_USER_DEVICE')")
    public ApiResponse<UserDeviceResponse> getUserDevice(@PathVariable("Id") Long id) {
        return ApiResponse.<UserDeviceResponse>builder()
                .data(userDeviceService.getUserDevice(id))
                .message("Get user device by ID")
                .build();
    }
}
