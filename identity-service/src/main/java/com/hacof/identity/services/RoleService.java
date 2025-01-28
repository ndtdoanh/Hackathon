package com.hacof.identity.services;

import java.util.List;

import com.hacof.identity.dtos.request.RoleCreateRequest;
import com.hacof.identity.dtos.request.RoleUpdateRequest;
import com.hacof.identity.dtos.response.RoleResponse;

public interface RoleService {
    RoleResponse createRole(RoleCreateRequest request);

    List<RoleResponse> getRoles();

    RoleResponse getRole(Long id);

    RoleResponse updateRole(Long id, RoleUpdateRequest request);

    RoleResponse getRoleFromToken(String token);

    void deleteRole(Long roleId);
}
