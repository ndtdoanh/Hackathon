package com.hacof.identity.services;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hacof.identity.dtos.request.RoleCreateRequest;
import com.hacof.identity.dtos.request.RoleUpdateRequest;
import com.hacof.identity.dtos.response.PermissionResponse;
import com.hacof.identity.dtos.response.RoleResponse;
import com.hacof.identity.entities.Role;
import com.hacof.identity.exceptions.AppException;
import com.hacof.identity.exceptions.ErrorCode;
import com.hacof.identity.mappers.RoleMapper;
import com.hacof.identity.mappers.UserMapper;
import com.hacof.identity.repositories.PermissionRepository;
import com.hacof.identity.repositories.RoleRepository;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    private final UserMapper userMapper;

    public RoleResponse createRole(RoleCreateRequest request) {

        if(roleRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }

        var role = roleMapper.toRole(request);

        var permissionsIds = request.getPermissions().stream().map(Long::valueOf).collect(Collectors.toSet());

        var permissions = permissionRepository.findAllById(permissionsIds);
        role.setPermissions(new HashSet<>(permissions));

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

    public List<RoleResponse> getRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public RoleResponse getRole(Long id) {
        return roleMapper.toRoleResponse(
                roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED)));
    }

    public RoleResponse updateRole(Long id, RoleUpdateRequest request) {

        var role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            role.setDescription(request.getDescription());
        }

        if (request.getPermissions() != null && !request.getPermissions().isEmpty()) {
            var permissions =
                    request.getPermissions().stream().map(Long::valueOf).collect(Collectors.toSet());

            var validPermissions = permissionRepository.findAllById(permissions);

            if (!validPermissions.isEmpty()) {
                role.setPermissions(new HashSet<>(validPermissions));
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

    public void deleteRole(Long roleId) {
        if (!roleRepository.existsById(roleId)) {
            throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
        }
        roleRepository.deleteById(roleId);
    }
}
