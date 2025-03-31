package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.TeamRequestMemberDTO;
import com.hacof.hackathon.entity.TeamRequestMember;

@Mapper(
        componentModel = "spring",
        //        uses = {UserMapper.class, TeamRequestMapper.class})
        uses = {TeamRequestMapper.class})
public interface TeamRequestMemberMapper {
    TeamRequestMemberDTO toDto(TeamRequestMember teamRequestMember);

    TeamRequestMember toEntity(TeamRequestMemberDTO teamRequestMemberDTO);

    void updateEntityFromDto(
            TeamRequestMemberDTO teamRequestMemberDTO, @MappingTarget TeamRequestMember teamRequestMember);
}
