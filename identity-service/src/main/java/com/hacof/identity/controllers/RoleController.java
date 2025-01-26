package com.hacof.identity.controllers;

import com.hacof.identity.dtos.request.ApiResponse;
import com.hacof.identity.dtos.request.PermissionRequest;
import com.hacof.identity.dtos.request.RoleRequest;
import com.hacof.identity.dtos.response.PermissionResponse;
import com.hacof.identity.dtos.response.RoleResponse;
import com.hacof.identity.services.RoleService;
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
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(@RequestBody RoleRequest request){
        RoleResponse roleResponse = roleService.createRole(request);
        ApiResponse<RoleResponse> response = ApiResponse.<RoleResponse>builder()
                .result(roleResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getRoles(){
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getRoles())
                .build();
    }

    @DeleteMapping("/{Id}")
    public ApiResponse<Void> deleteRole(@PathVariable("Id") Long id){
        roleService.deleteRole(id);
        return ApiResponse.<Void>builder().build();
    }
}
