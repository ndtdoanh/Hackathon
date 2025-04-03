package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.TeamDTO;

public interface TeamService {
    //    // Team Request Management
    //    TeamRequestDTO createTeamRequest(TeamRequestDTO request);
    //
    //    TeamRequestDTO approveTeamRequest(Long requestId, Long reviewerId);
    //
    //    TeamRequestDTO rejectTeamRequest(Long requestId, Long reviewerId);
    //
    //    TeamDTO createTeamFromRequest(TeamRequestDTO teamRequestDTO);
    //
    //    // Team Member Management
    //    UserTeamRequestDTO inviteMember(Long teamId, Long userId, Long inviterId);
    //
    //    UserTeamRequestDTO acceptInvitation(Long requestId, Long userId);
    //
    //    UserTeamRequestDTO rejectInvitation(Long requestId, Long userId);
    //
    //    UserTeamRequestDTO requestToJoinTeam(Long teamId, Long userId);
    //
    //    UserTeamRequestDTO approveJoinRequest(Long requestId, Long leaderId);
    //
    //    UserTeamRequestDTO rejectJoinRequest(Long requestId, Long leaderId);
    //
    //    // Team Hackathon Registration
    //    TeamDTO updateTeam(TeamDTO teamDTO);
    //
    //    TeamDTO getTeamById(Long id);
    //
    //    List<TeamDTO> getTeams(Specification<Team> spec);
    //
    //    void removeMember(UserTeamDTO memberDTO);
    //
    //    TeamDTO registerForHackathon(TeamDTO teamDTO);
    //
    //    List<TeamDTO> getAll();

    TeamDTO createTeam(TeamDTO teamDTO);

    TeamDTO updateTeam(long id, TeamDTO teamDTO);

    void deleteTeam(long id);

    TeamDTO getTeamById(long id);

    List<TeamDTO> getAllTeams();

    List<TeamDTO> getTeamsByUserIdAndHackathonId(Long userId, Long hackathonId);

    // List<TeamDTO> getTeamsByHackathon(long hackathonId);
}
