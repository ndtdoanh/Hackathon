package com.hacof.identity.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.request.PermissionCreateRequest;
import com.hacof.identity.dto.request.PermissionUpdateRequest;
import com.hacof.identity.dto.response.PermissionResponse;
import com.hacof.identity.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_PERMISSION')")
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission(
            @RequestBody @Valid PermissionCreateRequest request) {
        PermissionResponse permissionResponse = permissionService.createPermission(request);
        ApiResponse<PermissionResponse> response = ApiResponse.<PermissionResponse>builder()
                .data(permissionResponse)
                .message("Permission created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GET_PERMISSIONS')")
    public ApiResponse<List<PermissionResponse>> getPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .data(permissionService.getPermissions())
                .message("Get all permissions")
                .build();
    }

    @GetMapping("/{Id}")
    @PreAuthorize("hasAuthority('GET_PERMISSION')")
    public ApiResponse<PermissionResponse> getPermission(@PathVariable("Id") Long Id) {
        return ApiResponse.<PermissionResponse>builder()
                .data(permissionService.getPermission(Id))
                .message("Get permission by Id")
                .build();
    }

    @PutMapping("/{Id}")
    @PreAuthorize("hasAuthority('UPDATE_PERMISSION')")
    public ApiResponse<PermissionResponse> updatePermission(
            @PathVariable("Id") Long Id, @RequestBody PermissionUpdateRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .data(permissionService.updatePermission(Id, request))
                .message("Permission updated successfully")
                .build();
    }

    @DeleteMapping("/{Id}")
    @PreAuthorize("hasAuthority('DELETE_PERMISSION')")
    public ApiResponse<Void> deletePermission(@PathVariable("Id") Long id) {
        permissionService.deletePermission(id);
        return ApiResponse.<Void>builder()
                .message("Permission has been deleted")
                .build();
    }

    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    @PreAuthorize("hasAuthority('DELETE_PERMISSION_FROM_ROLE')")
    public ApiResponse<Void> deletePermissionFromRole(
            @PathVariable("roleId") Long roleId, @PathVariable("permissionId") Long permissionId) {
        permissionService.deletePermissionFromRole(roleId, permissionId);
        return ApiResponse.<Void>builder()
                .message("Permission has been removed from role")
                .build();
    }
}
