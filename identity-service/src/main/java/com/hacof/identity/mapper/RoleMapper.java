package com.hacof.identity.mapper;

import com.hacof.identity.dto.request.RoleCreateRequest;
import com.hacof.identity.dto.response.RoleResponse;
import com.hacof.identity.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "rolePermissions", ignore = true)
    Role toRole(RoleCreateRequest request);

    @Mapping(target = "id", expression = "java(String.valueOf(role.getId()))")
    @Mapping(
            target = "createdByUserName",
            expression = "java(role.getCreatedBy() != null ? role.getCreatedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    @Mapping(target = "permissions", source = "permissions")
    RoleResponse toRoleResponse(Role role);
}
