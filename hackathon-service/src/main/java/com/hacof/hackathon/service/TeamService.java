package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.TeamDTO;

public interface TeamService {
    List<TeamDTO> getAllTeams();

    TeamDTO getTeamById(Long id);

    TeamDTO createTeam(TeamDTO teamDTO);

    TeamDTO updateTeam(Long id, TeamDTO teamDTO);

    void deleteTeam(Long id);

    void inviteMember(Long teamId, String memberEmail);

    void assignMentor(Long teamId, Long mentorId);
}
