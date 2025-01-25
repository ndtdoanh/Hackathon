package com.hacof.identity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.identity.dtos.request.RoleRequest;
import com.hacof.identity.dtos.response.RoleResponse;
import com.hacof.identity.entities.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
