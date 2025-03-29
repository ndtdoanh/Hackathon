package com.hacof.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.identity.dto.request.RoleCreateRequest;
import com.hacof.identity.dto.response.RoleResponse;
import com.hacof.identity.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "rolePermissions", ignore = true)
    Role toRole(RoleCreateRequest request);

    @Mapping(target = "id", expression = "java(String.valueOf(role.getId()))")
    @Mapping(
            target = "createdByUserId",
            expression = "java(role.getCreatedBy() != null ? String.valueOf(role.getCreatedBy().getId()) : null)")
    @Mapping(target = "permissions", source = "permissions")
    RoleResponse toRoleResponse(Role role);
}
