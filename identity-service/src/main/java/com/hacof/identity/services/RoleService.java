package com.hacof.identity.services;

import com.hacof.identity.dtos.request.RoleRequest;
import com.hacof.identity.dtos.response.PermissionResponse;
import com.hacof.identity.dtos.response.RoleResponse;
import com.hacof.identity.entities.Permission;
import com.hacof.identity.mappers.RoleMapper;
import com.hacof.identity.repositories.PermissionRepository;
import com.hacof.identity.repositories.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse createRole(RoleRequest request) {
        var role = roleMapper.toRole(request);

        var permissionsIds = request.getPermissions().stream()
                        .map(Long::valueOf)
                .collect(Collectors.toSet());

        var permissions = permissionRepository.findAllById(permissionsIds);
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);

        RoleResponse roleResponse = roleMapper.toRoleResponse(role);

        roleResponse.setPermissions(
                role.getPermissions().stream()
                        .map(permission -> PermissionResponse.builder()
                                .id(permission.getId())
                                .name(permission.getName())
                                .apiPath(permission.getApiPath())
                                .method(permission.getMethod())
                                .module(permission.getModule())
                                .build()
                        )
                        .collect(Collectors.toSet())
        );

        return roleResponse;
    }

    public List<RoleResponse> getRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public void deleteRole(Long roleId) {
        roleRepository.deleteById(roleId);
    }
}
