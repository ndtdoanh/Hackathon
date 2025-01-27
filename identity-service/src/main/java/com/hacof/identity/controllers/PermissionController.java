package com.hacof.identity.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.identity.dtos.request.ApiResponse;
import com.hacof.identity.dtos.request.PermissionCreateRequest;
import com.hacof.identity.dtos.request.PermissionUpdateRequest;
import com.hacof.identity.dtos.response.PermissionResponse;
import com.hacof.identity.services.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission(
            @RequestBody @Valid PermissionCreateRequest request) {
        PermissionResponse permissionResponse = permissionService.createPermission(request);
        ApiResponse<PermissionResponse> response = ApiResponse.<PermissionResponse>builder()
                .result(permissionResponse)
                .message("Permission created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getPermissions())
                .message("Get all permissions")
                .build();
    }

    @GetMapping("/{Id}")
    public ApiResponse<PermissionResponse> getPermission(@PathVariable("Id") Long Id) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.getPermission(Id))
                .message("Get permission by Id")
                .build();
    }

    @PutMapping("/{Id}")
    public ApiResponse<PermissionResponse> updatePermission(
            @PathVariable("Id") Long Id, @RequestBody PermissionUpdateRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.updatePermission(Id, request))
                .message("Permission updated successfully")
                .build();
    }

    @DeleteMapping("/{Id}")
    public ApiResponse<Void> deletePermission(@PathVariable("Id") Long id) {
        permissionService.deletePermission(id);
        return ApiResponse.<Void>builder()
                .message("Permission has been deleted")
                .build();
    }
}
