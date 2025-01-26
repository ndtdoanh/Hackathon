package com.hacof.identity.controllers;

import com.hacof.identity.dtos.request.ApiResponse;
import com.hacof.identity.dtos.request.PermissionRequest;
import com.hacof.identity.dtos.response.PermissionResponse;
import com.hacof.identity.services.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission (@RequestBody PermissionRequest request) {
        PermissionResponse permissionResponse = permissionService.createPermission(request);
        ApiResponse<PermissionResponse> response = ApiResponse.<PermissionResponse>builder()
                .result(permissionResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getPermissions())
                .build();
    }

    @DeleteMapping("/{Id}")
    public ApiResponse<Void> deletePermission(@PathVariable("Id") Long id) {
        permissionService.deletePermission(id);
        return ApiResponse.<Void>builder().build();
    }

}
