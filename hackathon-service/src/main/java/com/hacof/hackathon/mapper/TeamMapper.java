package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.entity.Team;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    TeamDTO convertToDTO(Team team);

    Team convertToEntity(TeamDTO teamDTO);
}
