package com.hacof.identity.mapper;

import com.hacof.identity.dto.response.HackathonResponse;
import com.hacof.identity.dto.response.UserResponse;
import com.hacof.identity.entity.Hackathon;
import com.hacof.identity.entity.User;
import org.mapstruct.*;

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
    @Mapping(target = "user", source = "user")
    @Mapping(target = "hackathon", source = "hackathon")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    UserHackathonResponseDTO toUserHackathonResponse(UserHackathon userHackathon);

    // Mapping to the UserResponse from User entity
    @Named("mapUserToDto")
    default UserResponse mapUserToDto(User user) {
        if (user == null) {
            return null;
        }
        return UserResponse.builder()
                .id(String.valueOf(user.getId()))
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isVerified(user.getIsVerified())
                .status(user.getStatus())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }

    @Named("mapHackathonToDto")
    default HackathonResponse mapHackathonToDto(Hackathon hackathon) {
        if (hackathon == null) {
            return null;
        }
        return HackathonResponse.builder()
                .id(String.valueOf(hackathon.getId()))
                .title(hackathon.getTitle())
                .build();
    }
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
