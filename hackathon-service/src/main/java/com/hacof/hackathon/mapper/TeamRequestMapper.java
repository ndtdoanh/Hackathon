package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.entity.TeamRequest;

@Mapper(
        componentModel = "spring",
        uses = {HackathonMapper.class, UserMapper.class, TeamRequestMemberMapper.class})
public interface TeamRequestMapper {
    TeamRequestDTO toDto(TeamRequest teamRequest);

    TeamRequest toEntity(TeamRequestDTO teamRequestDTO);

    void updateEntityFromDto(TeamRequestDTO teamRequestDTO, @MappingTarget TeamRequest teamRequest);
}
