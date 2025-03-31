package com.hacof.hackathon.mapper;

import org.mapstruct.*;

import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.*;

@Mapper(componentModel = "spring")
public interface HackathonMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(hackathon.getId()))")
    @Mapping(
            target = "createdByUserName",
            expression = "java(hackathon.getCreatedBy() != null ? hackathon.getCreatedBy().getUsername() : null)")
    @Mapping(
            target = "lastModifiedByUserName",
            expression =
                    "java(hackathon.getLastModifiedBy() != null ? hackathon.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    @Mapping(target = "minimumTeamMembers", source = "minTeamSize")
    @Mapping(target = "maximumTeamMembers", source = "maxTeamSize")
    @Mapping(target = "enrollmentStatus", source = "status")
    @Mapping(target = "enrollStartDate", source = "startDate")
    @Mapping(target = "enrollEndDate", source = "endDate")
    @Mapping(target = "enrollmentCount", source = "maxTeams")
    HackathonDTO toDto(Hackathon hackathon);

    @Mapping(target = "minTeamSize", source = "minimumTeamMembers")
    @Mapping(target = "maxTeamSize", source = "maximumTeamMembers")
    @Mapping(target = "status", source = "enrollmentStatus")
    @Mapping(target = "startDate", source = "enrollStartDate")
    @Mapping(target = "endDate", source = "enrollEndDate")
    @Mapping(target = "maxTeams", source = "enrollmentCount")
    Hackathon toEntity(HackathonDTO hackathonDTO);

    //    @Mapping(target = "id", ignore = true)
    //    @Mapping(target = "createdBy", ignore = true)
    //    @Mapping(target = "createdDate", ignore = true)
    //    @Mapping(target = "lastModifiedBy", expression = "java(mapStringToUser(dto.getLastModifiedByUserName()))")
    //    @Mapping(target = "lastModifiedDate", source = "lastModifiedAt")
    //    void updateEntityFromDto(HackathonDTO dto, @MappingTarget Hackathon entity);

    default User mapStringToUser(String username) {
        if (username == null) {
            return null;
        }
        User user = new User();
        user.setUsername(username);
        return user;
    }
}
