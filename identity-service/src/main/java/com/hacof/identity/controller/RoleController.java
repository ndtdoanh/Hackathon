package com.hacof.identity.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.identity.dto.ApiRequest;
import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.request.RoleCreateRequest;
import com.hacof.identity.dto.request.RoleUpdateRequest;
import com.hacof.identity.dto.response.RoleResponse;
import com.hacof.identity.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(
            @RequestBody @Valid ApiRequest<RoleCreateRequest> request) {
        RoleResponse roleResponse = roleService.createRole(request.getData());
        ApiResponse<RoleResponse> response = ApiResponse.<RoleResponse>builder()
                .requestId(request.getRequestId())
                .requestDateTime(request.getRequestDateTime())
                .channel(request.getChannel())
                .data(roleResponse)
                .message("Role created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(roleService.getRoles())
                .message("Get all roles")
                .build();
    }

    @GetMapping("/{Id}")
    public ApiResponse<RoleResponse> getRole(@PathVariable("Id") Long Id) {
        return ApiResponse.<RoleResponse>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(roleService.getRole(Id))
                .message("Get role by Id")
                .build();
    }

    @GetMapping("/role-from-token")
    public ApiResponse<RoleResponse> getRoleFromToken(@RequestHeader("Authorization") String token) {
        RoleResponse roleResponse = roleService.getRoleFromToken(token.replace("Bearer ", ""));

        return ApiResponse.<RoleResponse>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(roleResponse)
                .message("Get role from token")
                .build();
    }

    @PutMapping("/{Id}")
    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    public ApiResponse<RoleResponse> updateRole(
            @PathVariable("Id") Long Id, @RequestBody ApiRequest<RoleUpdateRequest> request) {
        RoleResponse roleResponse = roleService.updateRole(Id, request.getData());
        return ApiResponse.<RoleResponse>builder()
                .requestId(request.getRequestId())
                .requestDateTime(request.getRequestDateTime())
                .channel(request.getChannel())
                .data(roleResponse)
                .message("Role updated successfully")
                .build();
    }

    @DeleteMapping("/{Id}")
    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    public ApiResponse<Void> deleteRole(@PathVariable("Id") Long id) {
        roleService.deleteRole(id);
        return ApiResponse.<Void>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .message("Role has been deleted")
                .build();
    }
}
