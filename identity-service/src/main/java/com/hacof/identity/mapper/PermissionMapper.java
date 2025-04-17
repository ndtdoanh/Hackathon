package com.hacof.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.identity.dto.request.PermissionCreateRequest;
import com.hacof.identity.dto.request.PermissionUpdateRequest;
import com.hacof.identity.dto.response.PermissionResponse;
import com.hacof.identity.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionCreateRequest request);

    @Mapping(target = "id", expression = "java(String.valueOf(permission.getId()))")
    PermissionResponse toPermissionResponse(Permission permission);

    void updatePermissionFromRequest(PermissionUpdateRequest request, @MappingTarget Permission permission);
}
