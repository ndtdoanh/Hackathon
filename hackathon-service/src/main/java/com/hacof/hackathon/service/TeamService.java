package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.TeamDTO;

public interface TeamService {
    TeamDTO createTeam(TeamDTO teamDTO);

    TeamDTO updateTeam(long id, TeamDTO teamDTO);

    void deleteTeam(long id);

    TeamDTO getTeamById(long id);

    List<TeamDTO> getAllTeams();

    List<TeamDTO> getTeamsByUserIdAndHackathonId(Long userId, Long hackathonId);

    // List<TeamDTO> getTeamsByHackathon(long hackathonId);
    TeamDTO createTeamWithParticipants(String teamName, List<Long> requestIds);
}
