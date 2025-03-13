package com.hacof.identity.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.hacof.identity.dto.request.UserCreateRequest;
import com.hacof.identity.dto.request.UserUpdateRequest;
import com.hacof.identity.dto.response.UserResponse;
import com.hacof.identity.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userRoles", ignore = true)
    User toUser(UserCreateRequest request);

    @Mapping(
            target = "roles",
            expression = "java(user.getUserRoles().stream()"
                    + ".map(userRole -> new com.hacof.identity.dto.response.RoleResponse("
                    + "userRole.getRole().getId(), "
                    + "userRole.getRole().getName(), "
                    + "userRole.getRole().getDescription(), "
                    + "userRole.getRole().getPermissions().stream()"
                    + ".map(permission -> new com.hacof.identity.dto.response.PermissionResponse("
                    + "permission.getId(), "
                    + "permission.getName(), "
                    + "permission.getApiPath(), "
                    + "permission.getMethod(), "
                    + "permission.getModule()))"
                    + ".collect(java.util.stream.Collectors.toSet()))"
                    + ").collect(java.util.stream.Collectors.toSet()))")
    UserResponse toUserResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "userRoles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
