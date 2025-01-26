package com.hacof.identity.services;

import com.hacof.identity.dtos.request.PermissionRequest;
import com.hacof.identity.dtos.response.PermissionResponse;
import com.hacof.identity.entities.Permission;
import com.hacof.identity.mappers.PermissionMapper;
import com.hacof.identity.repositories.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getPermissions() {
        return permissionRepository.findAll().stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void deletePermission(Long permissionId) {
        permissionRepository.deleteById(permissionId);
    }

}
