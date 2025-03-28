package com.hacof.identity.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.hacof.identity.dto.request.UserCreateRequest;
import com.hacof.identity.dto.request.UserUpdateRequest;
import com.hacof.identity.dto.response.RoleResponse;
import com.hacof.identity.dto.response.UserResponse;
import com.hacof.identity.entity.User;
import com.hacof.identity.entity.UserRole;

@Mapper(
        componentModel = "spring",
        uses = {RoleMapper.class})
public interface UserMapper {

    RoleMapper ROLE_MAPPER = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "userRoles", ignore = true)
    User toUser(UserCreateRequest request);

    @Mapping(target = "createdByUserId", source = "createdBy.id")
    @Mapping(target = "roles", source = "userRoles", qualifiedByName = "mapUserRolesToRoles")
    UserResponse toUserResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "userRoles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    @Named("mapUserRolesToRoles")
    default Set<RoleResponse> mapUserRolesToRoles(Set<UserRole> userRoles) {
        return userRoles == null
                ? null
                : userRoles.stream()
                        .map(userRole -> ROLE_MAPPER.toRoleResponse(userRole.getRole()))
                        .collect(Collectors.toSet());
    }
}
