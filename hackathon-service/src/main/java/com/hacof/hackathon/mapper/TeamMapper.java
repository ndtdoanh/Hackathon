package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.entity.Team;

@Mapper(
        componentModel = "spring",
        uses = {HackathonMapper.class})
public interface TeamMapper {

    @Mapping(target = "teamHackathons", source = "teamHackathons")
    TeamDTO toDto(Team team);

    @Mapping(target = "teamHackathons", source = "teamHackathons")
    Team toEntity(TeamDTO teamDTO);
}
