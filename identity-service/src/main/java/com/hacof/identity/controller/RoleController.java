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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(@RequestBody @Valid RoleCreateRequest request) {
        RoleResponse roleResponse = roleService.createRole(request);
        ApiResponse<RoleResponse> response = ApiResponse.<RoleResponse>builder()
                .data(roleResponse)
                .message("Role created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .data(roleService.getRoles())
                .message("Get all roles")
                .build();
    }

    @GetMapping("/{Id}")
    public ApiResponse<RoleResponse> getRole(@PathVariable("Id") Long Id) {
        return ApiResponse.<RoleResponse>builder()
                .data(roleService.getRole(Id))
                .message("Get role by Id")
                .build();
    }

    @GetMapping("/role-from-token")
    public ApiResponse<RoleResponse> getRoleFromToken(@RequestHeader("Authorization") String token) {
        RoleResponse roleResponse = roleService.getRoleFromToken(token.replace("Bearer ", ""));

        return ApiResponse.<RoleResponse>builder()
                .data(roleResponse)
                .message("Get role from token")
                .build();
    }

    @PutMapping("/{Id}")
    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    public ApiResponse<RoleResponse> updateRole(@PathVariable("Id") Long Id, @RequestBody RoleUpdateRequest request) {
        RoleResponse roleResponse = roleService.updateRole(Id, request);
        return ApiResponse.<RoleResponse>builder()
                .data(roleResponse)
                .message("Role updated successfully")
                .build();
    }

    @DeleteMapping("/{Id}")
    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    public ApiResponse<Void> deleteRole(@PathVariable("Id") Long id) {
        roleService.deleteRole(id);
        return ApiResponse.<Void>builder().message("Role has been deleted").build();
    }
}
