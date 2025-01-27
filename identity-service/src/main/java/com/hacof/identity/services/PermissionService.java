package com.hacof.identity.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.identity.dtos.request.PermissionCreateRequest;
import com.hacof.identity.dtos.request.PermissionUpdateRequest;
import com.hacof.identity.dtos.response.PermissionResponse;
import com.hacof.identity.entities.Permission;
import com.hacof.identity.exceptions.AppException;
import com.hacof.identity.exceptions.ErrorCode;
import com.hacof.identity.mappers.PermissionMapper;
import com.hacof.identity.repositories.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionCreateRequest request) {
        if (permissionRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }

        Permission permission = permissionMapper.toPermission(request);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getPermissions() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    public PermissionResponse getPermission(Long id) {
        return permissionMapper.toPermissionResponse(permissionRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED)));
    }

    public PermissionResponse updatePermission(Long id, PermissionUpdateRequest request) {
        Permission permission =
                permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));

        permissionMapper.updatePermissionFromRequest(request, permission);
        permission = permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    public void deletePermission(Long permissionId) {
        if (!permissionRepository.existsById(permissionId)) {
            throw new AppException(ErrorCode.PERMISSION_NOT_EXISTED);
        }
        permissionRepository.deleteById(permissionId);
    }
}
