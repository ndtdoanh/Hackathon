package com.hacof.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.hacof.identity.dto.request.UserHackathonRequestDTO;
import com.hacof.identity.dto.response.UserHackathonResponseDTO;
import com.hacof.identity.entity.UserHackathon;

@Mapper(componentModel = "spring")
public interface UserHackathonMapper {

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "hackathon.id", source = "hackathonId")
    @Mapping(target = "role", source = "role")
    UserHackathon toUserHackathon(UserHackathonRequestDTO request);

    @Mapping(target = "id", expression = "java(String.valueOf(userHackathon.getId()))")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "hackathonId", source = "hackathon.id")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    UserHackathonResponseDTO toUserHackathonResponse(UserHackathon userHackathon);

    @Mapping(target = "id", ignore = true)
    @Mapping(
            target = "user.id",
            source = "userId",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(
            target = "hackathon.id",
            source = "hackathonId",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(
            target = "role",
            source = "role",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserHackathonFromRequest(UserHackathonRequestDTO request, @MappingTarget UserHackathon userHackathon);
}
