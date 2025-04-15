package com.hacof.identity.service;

import com.hacof.identity.dto.request.RoleCreateRequest;
import com.hacof.identity.dto.request.RoleUpdateRequest;
import com.hacof.identity.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse createRole(RoleCreateRequest request);

    List<RoleResponse> getRoles();

    RoleResponse getRole(Long id);

    RoleResponse updateRole(Long id, RoleUpdateRequest request);

    RoleResponse getRoleFromToken(String token);

    void deleteRole(Long roleId);
}
