package com.hacof.identity.controller;

import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.request.UserDeviceTrackRequest;
import com.hacof.identity.dto.response.FileUrlResponse;
import com.hacof.identity.dto.response.UserDeviceTrackResponse;
import com.hacof.identity.service.UserDeviceTrackService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userDeviceTrackResponse)
                .message("UserDeviceTrack created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ApiResponse<List<UserDeviceTrackResponse>> getUserDeviceTracks() {
        return ApiResponse.<List<UserDeviceTrackResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userDeviceTrackService.getUserDeviceTracks())
                .message("Get all device tracks")
                .build();
    }

    @GetMapping("/{Id}")
    public ApiResponse<UserDeviceTrackResponse> getUserDeviceTrackById(@PathVariable("Id") Long id) {
        return ApiResponse.<UserDeviceTrackResponse>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userDeviceTrackService.getUserDeviceTrack(id))
                .message("Get device track by id")
                .build();
    }

    @GetMapping("/user-device/{userDeviceId}")
    public ApiResponse<List<UserDeviceTrackResponse>> getUserDeviceTracksByUserDeviceId(@PathVariable Long userDeviceId) {
        return ApiResponse.<List<UserDeviceTrackResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userDeviceTrackService.getUserDeviceTracksByUserDeviceId(userDeviceId))
                .message("Get device tracks by userDeviceId")
                .build();
    }

    @GetMapping("/{userDeviceTrackId}/file-urls")
    public ApiResponse<List<FileUrlResponse>> getFileUrlsByUserDeviceTrackId(@PathVariable Long userDeviceTrackId) {
        return ApiResponse.<List<FileUrlResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userDeviceTrackService.getFileUrlsByUserDeviceTrackId(userDeviceTrackId))
                .message("Get file URLs by userDeviceTrackId")
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
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userDeviceTrackResponse)
                .message("UserDeviceTrack updated successfully")
                .build());
    }

    @DeleteMapping("/{Id}")
    @PreAuthorize("hasAuthority('DELETE_USER_DEVICE_TRACK')")
    public ApiResponse<Void> deleteUserDeviceTrack(@PathVariable("Id") Long id) {
        userDeviceTrackService.deleteUserDeviceTrack(id);
        return ApiResponse.<Void>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .message("UserDeviceTrack deleted successfully")
                .build();
    }
}
