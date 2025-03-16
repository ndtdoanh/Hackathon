package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.DeviceDTO;
import com.hacof.hackathon.service.DeviceService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/devices")
@Slf4j
public class DeviceController {
    private final DeviceService deviceService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<DeviceDTO>>> getAllDevices() {
        List<DeviceDTO> devices = deviceService.getAllDevices();
        CommonResponse<List<DeviceDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Devices retrieved successfully"),
                devices);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<DeviceDTO>> createDevice(
            @RequestBody @Valid CommonRequest<DeviceDTO> request) {
        log.debug("Received request to create device: {}", request);
        DeviceDTO createdDevice = deviceService.createDevice(request.getData());
        CommonResponse<DeviceDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Device created successfully"),
                createdDevice);
        log.debug("Created device: {}", createdDevice);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<DeviceDTO>> updateDevice(
            @PathVariable Long id, @RequestBody @Valid CommonRequest<DeviceDTO> request) {
        log.debug("Received request to update device: {}", request);
        DeviceDTO updatedDevice = deviceService.updateDevice(id, request.getData());
        CommonResponse<DeviceDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Device updated successfully"),
                updatedDevice);
        log.debug("Updated device: {}", updatedDevice);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteDevice(@PathVariable Long id) {
        log.debug("Received request to delete device with id: {}", id);
        deviceService.deleteDevice(id);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Device deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }
}
