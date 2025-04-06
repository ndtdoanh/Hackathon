package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.constant.TeamRequestMemberStatus;
import com.hacof.hackathon.constant.TeamRequestStatus;
import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.dto.TeamRequestSearchDTO;

public interface TeamRequestService {
    TeamRequestDTO createTeamRequest(TeamRequestDTO request);

    TeamRequestDTO updateMemberResponse(String requestId, String userId, TeamRequestMemberStatus status, String note);

    TeamRequestDTO reviewTeamRequest(String requestId, TeamRequestStatus status, String note);

    List<TeamRequestDTO> searchTeamRequests(TeamRequestSearchDTO searchDTO);

    List<TeamRequestDTO> getAllByHackathonIdAndUserId(String hackathonId, String userId);

    List<TeamRequestDTO> filterByUserId(String userId);

    List<TeamRequestDTO> filterByHackathonId(String hackathonId);

    void deleteTeamRequest(Long teamRequestId);

    List<TeamRequestDTO> getAllTeamRequests();

    List<TeamRequestDTO> getTeamRequestsByMemberIdAndHackathonId(Long memberId, Long hackathonId);
}
