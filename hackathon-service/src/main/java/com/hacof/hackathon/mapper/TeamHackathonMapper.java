package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.TeamHackathonDTO;
import com.hacof.hackathon.entity.TeamHackathon;

@Mapper(componentModel = "spring")
public interface TeamHackathonMapper {
    // @Mapping(target = "team", ignore = true)
    TeamHackathonDTO toDto(TeamHackathon teamHackathon);

    // @Mapping(target = "team", ignore = true)
    TeamHackathon toEntity(TeamHackathonDTO teamHackathonDTO);
}
