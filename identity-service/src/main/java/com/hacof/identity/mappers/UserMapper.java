package com.hacof.identity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.identity.dtos.request.UserCreateRequest;
import com.hacof.identity.dtos.request.UserUpdateRequest;
import com.hacof.identity.dtos.response.UserResponse;
import com.hacof.identity.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
