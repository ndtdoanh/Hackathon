package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.TeamDTO;

public interface TeamService {
    TeamDTO createTeam(TeamDTO teamDTO, Long userId);

    TeamDTO addMemberToTeam(Long teamId, Long memberId);

    TeamDTO assignMentorToTeam(Long teamId, Long mentorId);

    List<TeamDTO> getAllTeams();

    TeamDTO updateTeam(Long teamId, TeamDTO teamDTO);

    void removeMemberFromTeam(Long teamId, Long memberId);

    void deleteTeam(Long teamId);
}
