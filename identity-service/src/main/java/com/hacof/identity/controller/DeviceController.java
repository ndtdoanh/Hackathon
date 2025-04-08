package com.hacof.identity.controller;

import java.io.IOException;
import java.util.List;

import com.hacof.identity.dto.response.FileUrlResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<ApiResponse<DeviceResponse>> createDevice(
            @ModelAttribute @Valid DeviceRequest request,
            @RequestParam(value = "files", required = false) List<MultipartFile> files)
            throws IOException {

        DeviceResponse deviceResponse = deviceService.createDevice(request, files);

        ApiResponse<DeviceResponse> response = ApiResponse.<DeviceResponse>builder()
                .data(deviceResponse)
                .message("Device created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ApiResponse<List<DeviceResponse>> getAllDevices() {
        return ApiResponse.<List<DeviceResponse>>builder()
                .data(deviceService.getDevices())
                .message("Get all devices")
                .build();
    }

    @GetMapping("/{Id}")
    public ApiResponse<DeviceResponse> getDeviceById(@PathVariable("Id") Long id) {
        return ApiResponse.<DeviceResponse>builder()
                .data(deviceService.getDevice(id))
                .message("Get device by Id")
                .build();
    }

    @GetMapping("/round/{roundId}")
    public ApiResponse<List<DeviceResponse>> getDevicesByRoundId(@PathVariable String roundId) {
        return ApiResponse.<List<DeviceResponse>>builder()
                .data(deviceService.getDevicesByRoundId(roundId))
                .message("Get devices by roundId")
                .build();
    }

    @GetMapping("/round-location/{roundLocationId}")
    public ApiResponse<List<DeviceResponse>> getDevicesByRoundLocationId(@PathVariable String roundLocationId) {
        return ApiResponse.<List<DeviceResponse>>builder()
                .data(deviceService.getDevicesByRoundLocationId(roundLocationId))
                .message("Get devices by roundLocationId")
                .build();
    }

    @GetMapping("/{deviceId}/file-urls")
    public ApiResponse<List<FileUrlResponse>> getFileUrlsByDeviceId(@PathVariable Long deviceId) {
        return ApiResponse.<List<FileUrlResponse>>builder()
                .data(deviceService.getFileUrlsByDeviceId(deviceId))
                .message("Get file URLs by deviceId")
                .build();
    }

    @GetMapping("/file-urls/{id}")
    public ApiResponse<FileUrlResponse> getFileUrlById(@PathVariable Long id) {
        return ApiResponse.<FileUrlResponse>builder()
                .data(deviceService.getFileUrlById(id))
                .message("Get file URL by ID")
                .build();
    }

    @PutMapping("/{Id}")
    @PreAuthorize("hasAuthority('UPDATE_DEVICE')")
    public ResponseEntity<ApiResponse<DeviceResponse>> updateDevice(
            @PathVariable("Id") Long id,
            @ModelAttribute DeviceRequest request,
            @RequestParam(value = "files", required = false) List<MultipartFile> files)
            throws IOException {

        DeviceResponse updatedDevice = deviceService.updateDevice(id, request, files);

        return ResponseEntity.ok(ApiResponse.<DeviceResponse>builder()
                .data(updatedDevice)
                .message("Device updated successfully")
                .build());
    }

    @DeleteMapping("/{Id}")
    @PreAuthorize("hasAuthority('DELETE_DEVICE')")
    public ApiResponse<Void> deleteDevice(@PathVariable("Id") Long id) {
        deviceService.deleteDevice(id);
        return ApiResponse.<Void>builder().message("Device has been deleted").build();
    }
}
