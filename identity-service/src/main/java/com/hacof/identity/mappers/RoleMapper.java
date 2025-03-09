package com.hacof.identity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.identity.dtos.request.RoleCreateRequest;
import com.hacof.identity.dtos.response.RoleResponse;
import com.hacof.identity.entities.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleCreateRequest request);

    RoleResponse toRoleResponse(Role role);
}
