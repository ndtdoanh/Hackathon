package com.hacof.identity.services;

import java.util.List;

import com.hacof.identity.dtos.request.PermissionCreateRequest;
import com.hacof.identity.dtos.request.PermissionUpdateRequest;
import com.hacof.identity.dtos.response.PermissionResponse;

public interface PermissionService {
    PermissionResponse createPermission(PermissionCreateRequest request);

    List<PermissionResponse> getPermissions();

    PermissionResponse getPermission(Long id);

    PermissionResponse updatePermission(Long id, PermissionUpdateRequest request);

    void deletePermission(Long permissionId);
}
