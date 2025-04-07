package com.hacof.hackathon.mapper.manual;

import java.util.List;
import java.util.stream.Collectors;

import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.dto.TeamRequestMemberDTO;
import com.hacof.hackathon.entity.TeamRequest;

public class TeamRequestMapperManual {
    public static TeamRequestDTO toDto(TeamRequest teamRequest) {
        if (teamRequest == null) {
            return null;
        }

        TeamRequestDTO dto = new TeamRequestDTO();
        dto.setId(String.valueOf(teamRequest.getId()));
        dto.setHackathonId(String.valueOf(teamRequest.getHackathon().getId()));
        dto.setName(teamRequest.getName());
        dto.setStatus(teamRequest.getStatus().toString());
        dto.setConfirmationDeadline(teamRequest.getConfirmationDeadline().toString());
        dto.setNote(teamRequest.getNote());
        dto.setCreatedByUserName(
                teamRequest.getCreatedBy() != null ? teamRequest.getCreatedBy().getUsername() : null);
        dto.setLastModifiedByUserName(
                teamRequest.getLastModifiedBy() != null
                        ? teamRequest.getLastModifiedBy().getUsername()
                        : null);
        dto.setCreatedAt(teamRequest.getCreatedDate());
        dto.setUpdatedAt(teamRequest.getLastModifiedDate());

        // Convert TeamRequestMembers
        List<TeamRequestMemberDTO> memberDTOs = teamRequest.getTeamRequestMembers().stream()
                .map(member -> {
                    TeamRequestMemberDTO memberDTO = new TeamRequestMemberDTO();
                    memberDTO.setId(String.valueOf(member.getId()));
                    memberDTO.setUser(UserMapperManual.toDto(member.getUser()));
                    memberDTO.setStatus(member.getStatus().toString());
                    //                    memberDTO.setRespondedAt(member.getRespondedAt());
                    //                    memberDTO.setCreatedByUserName(member.getCreatedBy() != null ?
                    // member.getCreatedBy().getUsername() : null);
                    //                    memberDTO.setCreatedAt(member.getCreatedDate());
                    //                    memberDTO.setLastModifiedByUserName(member.getLastModifiedBy() != null ?
                    // member.getLastModifiedBy().getUsername() : null);
                    //                    memberDTO.setUpdatedAt(member.getLastModifiedDate());

                    return memberDTO;
                })
                .collect(Collectors.toList());

        dto.setTeamRequestMembers(memberDTOs);

        return dto;
    }
}
