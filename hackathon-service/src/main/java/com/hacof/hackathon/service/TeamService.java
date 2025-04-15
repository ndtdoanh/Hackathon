package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.TeamBulkRequestDTO;
import com.hacof.hackathon.dto.TeamDTO;

import java.util.List;

public interface TeamService {
    TeamDTO updateTeam(long id, TeamDTO teamDTO);

    void deleteTeam(long id);

    TeamDTO getTeamById(long id);

    List<TeamDTO> getAllTeams();

    List<TeamDTO> getTeamsByUserIdAndHackathonId(Long userId, Long hackathonId);

    // TeamDTO createTeamWithParticipants(String teamName, List<Long> requestIds);
    List<TeamDTO> createBulkTeams(String teamLeaderId, List<Long> userIds);

    List<TeamDTO> createBulkTeams(List<TeamBulkRequestDTO> bulkRequest);

    //    List<TeamDTO> updateBulkTeams(List<TeamBulkRequestDTO> bulkRequest);
}
