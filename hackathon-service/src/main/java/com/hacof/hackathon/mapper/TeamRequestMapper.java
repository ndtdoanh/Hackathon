package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.entity.TeamRequest;
import com.hacof.hackathon.entity.User;

@Mapper(componentModel = "spring")
public interface TeamRequestMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(teamRequest.getId()))")
    @Mapping(
            target = "createdBy",
            expression = "java(teamRequest.getCreatedBy() != null ? teamRequest.getCreatedBy().getUsername() : null)")
    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(source = "hackathon.id", target = "hackathonId")
    TeamRequestDTO toDto(TeamRequest teamRequest);

    @Mapping(source = "hackathonId", target = "hackathon.id")
    TeamRequest toEntity(TeamRequestDTO teamRequestDTO);

    void updateEntityFromDto(TeamRequestDTO teamRequestDTO, @MappingTarget TeamRequest teamRequest);

    default User mapStringToUser(String username) {
        if (username == null) {
            return null;
        }
        User user = new User();
        user.setUsername(username);
        return user;
    }
}
