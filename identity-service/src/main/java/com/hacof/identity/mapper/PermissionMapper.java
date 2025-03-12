package com.hacof.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.hacof.identity.dto.request.PermissionCreateRequest;
import com.hacof.identity.dto.request.PermissionUpdateRequest;
import com.hacof.identity.dto.response.PermissionResponse;
import com.hacof.identity.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionCreateRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

    void updatePermissionFromRequest(PermissionUpdateRequest request, @MappingTarget Permission permission);
}
