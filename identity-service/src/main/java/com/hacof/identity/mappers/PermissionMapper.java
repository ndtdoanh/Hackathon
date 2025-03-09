package com.hacof.identity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.hacof.identity.dtos.request.PermissionCreateRequest;
import com.hacof.identity.dtos.request.PermissionUpdateRequest;
import com.hacof.identity.dtos.response.PermissionResponse;
import com.hacof.identity.entities.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionCreateRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

    void updatePermissionFromRequest(PermissionUpdateRequest request, @MappingTarget Permission permission);
}
