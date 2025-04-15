package com.hacof.identity.service;

import com.hacof.identity.dto.request.PermissionCreateRequest;
import com.hacof.identity.dto.request.PermissionUpdateRequest;
import com.hacof.identity.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse createPermission(PermissionCreateRequest request);

    List<PermissionResponse> getPermissions();

    PermissionResponse getPermission(Long id);

    PermissionResponse updatePermission(Long id, PermissionUpdateRequest request);

    void deletePermission(Long permissionId);

    void deletePermissionFromRole(Long roleId, Long permissionId);
}
