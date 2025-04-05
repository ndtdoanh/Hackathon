package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.entity.Team;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    // @Mapping(target = "teamLeaderId", source = "teamLeader.id")
    TeamDTO toDto(Team team);

    // @Mapping(target = "teamLeader", source = "teamLeaderId")

    Team toEntity(TeamDTO teamDTO);
}
