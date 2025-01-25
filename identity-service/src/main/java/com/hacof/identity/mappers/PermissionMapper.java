package com.hacof.identity.mappers;

import org.mapstruct.Mapper;

import com.hacof.identity.dtos.request.PermissionRequest;
import com.hacof.identity.dtos.response.PermissionResponse;
import com.hacof.identity.entities.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
