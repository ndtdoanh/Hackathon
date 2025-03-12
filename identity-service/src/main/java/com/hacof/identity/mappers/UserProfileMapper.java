package com.hacof.identity.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.hacof.identity.dtos.request.UserProfileCreateRequest;
import com.hacof.identity.dtos.request.UserProfileUpdateRequest;
import com.hacof.identity.dtos.response.UserProfileResponse;
import com.hacof.identity.entities.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "avatarUrl", ignore = true)
    @Mapping(target = "uploadedAt", ignore = true)
    UserProfile toEntity(UserProfileCreateRequest request);

    @Mapping(
            target = "name",
            expression = "java(userProfile.getUser().getFirstName() + \" \" + userProfile.getUser().getLastName())")
    UserProfileResponse toResponse(UserProfile userProfile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget UserProfile userProfile, UserProfileUpdateRequest request);
}
