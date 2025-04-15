package com.hacof.hackathon.mapper.manual;

import java.time.LocalDateTime;

import com.hacof.hackathon.constant.TeamRequestMemberStatus;
import com.hacof.hackathon.dto.TeamRequestMemberDTO;
import com.hacof.hackathon.entity.TeamRequest;
import com.hacof.hackathon.entity.TeamRequestMember;
import com.hacof.hackathon.entity.User;

public class TeamRequestMemberMapperManual {

    public static TeamRequestMember toEntity(TeamRequestMemberDTO dto, TeamRequest teamRequest, User user) {
        TeamRequestMember member = new TeamRequestMember();
        member.setTeamRequest(teamRequest);
        member.setUser(user);
        member.setStatus(TeamRequestMemberStatus.valueOf(dto.getStatus()));
        member.setRespondedAt(dto.getRespondedAt() != null ? LocalDateTime.parse(dto.getRespondedAt()) : null);
        //        member.setCreatedByUserName(dto.getCreatedByUserName());
        //        member.setCreatedAt(dto.getCreatedAt());
        //        member.setLastModifiedByUserName(dto.getLastModifiedByUserName());
        //        member.setUpdatedAt(dto.getUpdatedAt());
        return member;
    }

    public static TeamRequestMemberDTO toDto(TeamRequestMember member) {
        TeamRequestMemberDTO dto = new TeamRequestMemberDTO();
        dto.setId(String.valueOf(member.getId()));
        dto.setTeamRequestId(String.valueOf(member.getTeamRequest().getId()));
        dto.setUser(UserMapperManual.toDto(member.getUser()));
        dto.setStatus(member.getStatus().name());
        dto.setRespondedAt(
                member.getRespondedAt() != null ? member.getRespondedAt().toString() : null);
        // dto.setCreatedByUserName(member.getCreatedByUserName());
        // dto.setCreatedAt(member.getCreatedAt());
        // dto.setLastModifiedByUserName(member.getLastModifiedByUserName());
        // dto.setUpdatedAt(member.getUpdatedAt());
        return dto;
    }
}
