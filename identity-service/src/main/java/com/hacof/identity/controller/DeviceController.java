package com.hacof.identity.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.request.DeviceRequest;
import com.hacof.identity.dto.response.DeviceResponse;
import com.hacof.identity.service.DeviceService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeviceController {
    DeviceService deviceService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_DEVICE')")
    public ResponseEntity<ApiResponse<DeviceResponse>> createDevice(@RequestBody @Valid DeviceRequest request) {
        DeviceResponse deviceResponse = deviceService.createDevice(request);
        ApiResponse<DeviceResponse> response = ApiResponse.<DeviceResponse>builder()
                .data(deviceResponse)
                .message("Device created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GET_DEVICES')")
    public ApiResponse<List<DeviceResponse>> getAllDevices() {
        return ApiResponse.<List<DeviceResponse>>builder()
                .data(deviceService.getDevices())
                .message("Get all devices")
                .build();
    }

    @GetMapping("/{Id}")
    @PreAuthorize("hasAuthority('GET_DEVICE')")
    public ApiResponse<DeviceResponse> getDeviceById(@PathVariable("Id") Long id) {
        return ApiResponse.<DeviceResponse>builder()
                .data(deviceService.getDevice(id))
                .message("Get device by Id")
                .build();
    }

    @PutMapping("/{Id}")
    @PreAuthorize("hasAuthority('UPDATE_DEVICE')")
    public ApiResponse<DeviceResponse> updateDevice(@PathVariable("Id") Long id, @RequestBody DeviceRequest request) {
        return ApiResponse.<DeviceResponse>builder()
                .data(deviceService.updateDevice(id, request))
                .message("Device updated successfully")
                .build();
    }

    @DeleteMapping("/{Id}")
    @PreAuthorize("hasAuthority('DELETE_DEVICE')")
    public ApiResponse<Void> deleteDevice(@PathVariable("Id") Long id) {
        deviceService.deleteDevice(id);
        return ApiResponse.<Void>builder().message("Device has been deleted").build();
    }
}
