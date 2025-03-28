package com.hacof.identity.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.hacof.identity.dto.request.UserProfileCreateRequest;
import com.hacof.identity.dto.request.UserProfileUpdateRequest;
import com.hacof.identity.dto.response.UserProfileResponse;
import com.hacof.identity.entity.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "avatarUrl", ignore = true)
    @Mapping(target = "uploadedAt", ignore = true)
    UserProfile toEntity(UserProfileCreateRequest request);

    @Mapping(target = "userId", source = "user.id")
    UserProfileResponse toResponse(UserProfile userProfile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget UserProfile userProfile, UserProfileUpdateRequest request);
}
