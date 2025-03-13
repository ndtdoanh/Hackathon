package com.hacof.identity.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hacof.identity.dto.request.RoleCreateRequest;
import com.hacof.identity.dto.request.RoleUpdateRequest;
import com.hacof.identity.dto.response.PermissionResponse;
import com.hacof.identity.dto.response.RoleResponse;
import com.hacof.identity.entity.Permission;
import com.hacof.identity.entity.Role;
import com.hacof.identity.entity.RolePermission;
import com.hacof.identity.exception.AppException;
import com.hacof.identity.exception.ErrorCode;
import com.hacof.identity.mapper.RoleMapper;
import com.hacof.identity.mapper.UserMapper;
import com.hacof.identity.repository.PermissionRepository;
import com.hacof.identity.repository.RoleRepository;
import com.hacof.identity.service.RoleService;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    private final UserMapper userMapper;

    @Override
    public RoleResponse createRole(RoleCreateRequest request) {

        if (roleRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }

        var role = roleMapper.toRole(request);
        var permissionsIds =
                request.getPermissions().stream().map(Long::valueOf).collect(Collectors.toSet());

        var permissions = permissionRepository.findAllById(permissionsIds);

        role = roleRepository.save(role);
        final Role finalRole = role;

        var rolePermissions = permissions.stream()
                .map(permission -> RolePermission.builder()
                        .role(finalRole)
                        .permission(permission)
                        .build())
                .collect(Collectors.toSet());

        role.setRolePermissions(rolePermissions);
        role = roleRepository.save(role);

        RoleResponse roleResponse = roleMapper.toRoleResponse(role);

        roleResponse.setPermissions(role.getRolePermissions().stream()
                .map(rolePermission -> {
                    Permission permission = rolePermission.getPermission();
                    return PermissionResponse.builder()
                            .id(permission.getId())
                            .name(permission.getName())
                            .apiPath(permission.getApiPath())
                            .method(permission.getMethod())
                            .module(permission.getModule())
                            .build();
                })
                .collect(Collectors.toSet()));

        return roleResponse;
    }

    @Override
    public List<RoleResponse> getRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    @Override
    public RoleResponse getRole(Long id) {
        return roleMapper.toRoleResponse(
                roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED)));
    }

    @Override
    public RoleResponse updateRole(Long id, RoleUpdateRequest request) {

        var role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            role.setDescription(request.getDescription());
        }

        if (request.getPermissions() != null && !request.getPermissions().isEmpty()) {
            var permissions =
                    request.getPermissions().stream().map(Long::valueOf).collect(Collectors.toSet());

            var validPermissions = permissionRepository.findAllById(permissions);

            for (Permission permission : validPermissions) {
                boolean alreadyExists = role.getRolePermissions().stream()
                        .anyMatch(rp -> rp.getPermission().getId() == permission.getId());

                if (!alreadyExists) {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRole(role);
                    rolePermission.setPermission(permission);
                    role.getRolePermissions().add(rolePermission);
                }
            }
        }

        role = roleRepository.save(role);

        RoleResponse roleResponse = roleMapper.toRoleResponse(role);

        roleResponse.setPermissions(role.getPermissions().stream()
                .map(permission -> PermissionResponse.builder()
                        .id(permission.getId())
                        .name(permission.getName())
                        .apiPath(permission.getApiPath())
                        .method(permission.getMethod())
                        .module(permission.getModule())
                        .build())
                .collect(Collectors.toSet()));

        return roleResponse;
    }

    @Override
    public RoleResponse getRoleFromToken(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            JWTClaimsSet claims = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());

            String roleName = claims.getStringClaim("role");
            if (roleName == null || roleName.isBlank()) {
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }

            Role role =
                    roleRepository.findByName(roleName).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

            RoleResponse roleResponse = roleMapper.toRoleResponse(role);

            return roleResponse;

        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }

    @Override
    public void deleteRole(Long roleId) {
        if (!roleRepository.existsById(roleId)) {
            throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
        }
        roleRepository.deleteById(roleId);
    }
}
