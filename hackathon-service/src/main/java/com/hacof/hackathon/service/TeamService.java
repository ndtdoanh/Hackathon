package com.hacof.hackathon.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.dto.UserTeamDTO;
import com.hacof.hackathon.dto.UserTeamRequestDTO;
import com.hacof.hackathon.entity.Team;

public interface TeamService {
    // Team Request Management
    TeamRequestDTO createTeamRequest(TeamRequestDTO request);

    TeamRequestDTO approveTeamRequest(Long requestId, Long reviewerId);

    TeamRequestDTO rejectTeamRequest(Long requestId, Long reviewerId);

    TeamDTO createTeamFromRequest(TeamRequestDTO teamRequestDTO);

    // Team Member Management
    UserTeamRequestDTO inviteMember(Long teamId, Long userId, Long inviterId);

    UserTeamRequestDTO acceptInvitation(Long requestId, Long userId);

    UserTeamRequestDTO rejectInvitation(Long requestId, Long userId);

    UserTeamRequestDTO requestToJoinTeam(Long teamId, Long userId);

    UserTeamRequestDTO approveJoinRequest(Long requestId, Long leaderId);

    UserTeamRequestDTO rejectJoinRequest(Long requestId, Long leaderId);

    // Team Hackathon Registration
    TeamDTO updateTeam(TeamDTO teamDTO);

    TeamDTO getTeamById(Long id);

    List<TeamDTO> getTeams(Specification<Team> spec);

    void removeMember(UserTeamDTO memberDTO);

    TeamDTO registerForHackathon(TeamDTO teamDTO);

    List<TeamDTO> getAll();
}
