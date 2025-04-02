package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    @Mapping(target = "teamHackathons", ignore = true)
    TeamDTO toDto(Team team);

    @Mapping(target = "teamHackathons", ignore = true)
    Team toEntity(TeamDTO teamDTO);

    default String map(User user) {
        return user != null ? user.getUsername() : null;
    }
}
