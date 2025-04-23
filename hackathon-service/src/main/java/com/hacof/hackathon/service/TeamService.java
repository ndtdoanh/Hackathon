package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.TeamBulkRequestDTO;
import com.hacof.hackathon.dto.TeamDTO;

public interface TeamService {
    TeamDTO updateTeam(long id, TeamDTO teamDTO);

    void deleteTeam(long id);

    TeamDTO getTeamById(long id);

    List<TeamDTO> getAllTeams();

    List<TeamDTO> getTeamsByUserIdAndHackathonId(Long userId, Long hackathonId);

    List<TeamDTO> createBulkTeams(String teamLeaderId, List<Long> userIds);

    List<TeamDTO> createBulkTeams(List<TeamBulkRequestDTO> bulkRequest);

    // update 23/4/25
    List<TeamDTO> getTeamsByHackathonId(Long hackathonId);
}
