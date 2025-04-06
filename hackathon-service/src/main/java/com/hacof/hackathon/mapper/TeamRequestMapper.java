package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.entity.TeamRequest;
import com.hacof.hackathon.entity.User;

@Mapper(componentModel = "spring")
public interface TeamRequestMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(teamRequest.getId()))")
    @Mapping(target = "hackathonId", expression = "java(String.valueOf(teamRequest.getHackathon().getId()))")
    @Mapping(target = "reviewedBy", source = "reviewedBy", qualifiedByName = "userToString")
    @Mapping(
            target = "createdByUserName",
            expression = "java(teamRequest.getCreatedBy() != null ? teamRequest.getCreatedBy().getUsername() : null)")
    @Mapping(
            target = "lastModifiedByUserName",
            expression =
                    "java(teamRequest.getLastModifiedBy() != null ? teamRequest.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    TeamRequestDTO toDto(TeamRequest teamRequest);

    @Mapping(target = "hackathon.id", source = "hackathonId", qualifiedByName = "stringToLong")
    @Mapping(target = "reviewedBy", source = "reviewedBy", qualifiedByName = "stringToUser")
    TeamRequest toEntity(TeamRequestDTO teamRequestDTO);

    // void updateEntityFromDto(TeamRequestDTO dto, @MappingTarget TeamRequest entity);

    @Named("userToString")
    default String userToString(User user) {
        return user != null ? String.valueOf(user.getId()) : null;
    }

    @Named("stringToUser")
    default User stringToUser(String id) {
        if (id == null) return null;
        User user = new User();
        user.setId(Long.parseLong(id));
        return user;
    }

    @Named("stringToLong")
    default Long stringToLong(String id) {
        return id != null ? Long.parseLong(id) : null;
    }
}
