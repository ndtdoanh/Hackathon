package com.hacof.identity.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.request.LogDeviceStatusRequest;
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
    public ApiResponse<UserDeviceTrackResponse> addDeviceTrack(@RequestBody @Valid LogDeviceStatusRequest request) {
        return ApiResponse.<UserDeviceTrackResponse>builder()
                .result(userDeviceTrackService.addDeviceTrack(request))
                .message("Device track added successfully")
                .build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GET_USER_DEVICE_TRACKS')")
    public ApiResponse<List<UserDeviceTrackResponse>> getDeviceTracks() {
        return ApiResponse.<List<UserDeviceTrackResponse>>builder()
                .result(userDeviceTrackService.getDeviceTracks())
                .message("Get all device tracks")
                .build();
    }
}
