package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.UserTeamRequestDTO;

@Mapper(
        componentModel = "spring",
        uses = {UserMapper.class, TeamMapper.class})
public interface UserTeamRequestMapper {

    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "user.id", target = "userId")
    UserTeamRequestDTO toDTO(UserTeamRequest entity);

    @Mapping(target = "team", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", ignore = true)
    UserTeamRequest toEntity(UserTeamRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntityFromDTO(UserTeamRequestDTO dto, @MappingTarget UserTeamRequest entity);
}
