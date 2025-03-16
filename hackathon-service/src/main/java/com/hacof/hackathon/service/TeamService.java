package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.TeamDTO;

public interface TeamService {
    TeamDTO createTeam(TeamDTO teamDTO, Long creatorId);

    TeamDTO inviteMemberToTeam(Long teamId, Long userId);

    TeamDTO requestToJoinTeam(Long teamId, Long userId);

    List<TeamDTO> getAllTeams();
}
