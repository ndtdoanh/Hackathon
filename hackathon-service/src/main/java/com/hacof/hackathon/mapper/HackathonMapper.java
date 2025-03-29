package com.hacof.hackathon.mapper;

import org.mapstruct.*;

import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.*;

@Mapper(componentModel = "spring")
public interface HackathonMapper {
    @Mapping(source = "id", target = "id", qualifiedByName = "mapIdToString")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "subTitle", target = "subTitle")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "information", target = "information")
    @Mapping(source = "bannerImageUrl", target = "bannerImageUrl")
    @Mapping(source = "startDate", target = "enrollStartDate")
    @Mapping(source = "endDate", target = "enrollEndDate")
    @Mapping(source = "minTeamSize", target = "minimumTeamMembers")
    @Mapping(source = "maxTeamSize", target = "maximumTeamMembers")
    @Mapping(source = "contact", target = "contact")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdBy", target = "createdBy", qualifiedByName = "mapUserToUsername")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "lastModifiedBy", target = "lastModifiedBy", qualifiedByName = "mapUserToUsername")
    @Mapping(source = "lastModifiedDate", target = "lastModifiedDate")
    HackathonDTO toDto(Hackathon hackathon);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "title", target = "title")
    @Mapping(source = "information", target = "information")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "bannerImageUrl", target = "bannerImageUrl")
    @Mapping(source = "enrollStartDate", target = "startDate")
    @Mapping(source = "enrollEndDate", target = "endDate")
    @Mapping(source = "minimumTeamMembers", target = "minTeamSize")
    @Mapping(source = "maximumTeamMembers", target = "maxTeamSize")
    @Mapping(source = "status", target = "status")
    Hackathon toEntity(HackathonDTO hackathonDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "title", target = "title")
    @Mapping(source = "information", target = "information")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "bannerImageUrl", target = "bannerImageUrl")
    @Mapping(source = "enrollStartDate", target = "startDate")
    @Mapping(source = "enrollEndDate", target = "endDate")
    @Mapping(source = "minimumTeamMembers", target = "minTeamSize")
    @Mapping(source = "maximumTeamMembers", target = "maxTeamSize")
    @Mapping(source = "status", target = "status")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(source = "lastModifiedBy", target = "lastModifiedBy", qualifiedByName = "mapUsernameToUser")
    @Mapping(source = "lastModifiedDate", target = "lastModifiedDate")
    void updateEntityFromDto(HackathonDTO dto, @MappingTarget Hackathon entity);

    @Named("mapIdToString")
    default String mapIdToString(Long id) {
        return (id != null) ? String.valueOf(id) : null;
    }

    @Named("mapStringToId")
    default Long mapStringToId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ID format: " + id);
        }
    }

    @Named("mapUserToUsername")
    default String mapUserToUsername(User user) {
        return user != null ? user.getUsername() : null;
    }

    @Named("mapUsernameToUser")
    default User mapUsernameToUser(String username) {
        if (username == null) {
            return null;
        }
        User user = new User();
        user.setUsername(username);
        user.setId(1L);
        return user;
    }
}
