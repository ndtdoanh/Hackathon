package com.hacof.identity.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.identity.dto.request.PermissionCreateRequest;
import com.hacof.identity.dto.request.PermissionUpdateRequest;
import com.hacof.identity.dto.response.PermissionResponse;
import com.hacof.identity.entity.Permission;
import com.hacof.identity.entity.Role;
import com.hacof.identity.entity.RolePermission;
import com.hacof.identity.exception.AppException;
import com.hacof.identity.exception.ErrorCode;
import com.hacof.identity.mapper.PermissionMapper;
import com.hacof.identity.repository.PermissionRepository;
import com.hacof.identity.repository.RoleRepository;
import com.hacof.identity.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;
    RoleRepository roleRepository;

    @Override
    public PermissionResponse createPermission(PermissionCreateRequest request) {
        if (permissionRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }

        Permission permission = permissionMapper.toPermission(request);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getPermissions() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    @Override
    public PermissionResponse getPermission(Long id) {
        return permissionMapper.toPermissionResponse(permissionRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED)));
    }

    @Override
    public PermissionResponse updatePermission(Long id, PermissionUpdateRequest request) {
        Permission permission =
                permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));

        permissionMapper.updatePermissionFromRequest(request, permission);
        permission = permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public void deletePermission(Long permissionId) {
        if (!permissionRepository.existsById(permissionId)) {
            throw new AppException(ErrorCode.PERMISSION_NOT_EXISTED);
        }
        permissionRepository.deleteById(permissionId);
    }

    @Override
    public void deletePermissionFromRole(Long roleId, Long permissionId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        Permission permission = permissionRepository
                .findById(permissionId)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));

        RolePermission rolePermission = role.getRolePermissions().stream()
                .filter(rp -> rp.getPermission().equals(permission))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_IN_ROLE));

        role.getRolePermissions().remove(rolePermission);
        roleRepository.save(role);
    }
}
