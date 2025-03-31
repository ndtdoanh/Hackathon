package com.hacof.hackathon.service;

import org.springframework.data.domain.Page;

import com.hacof.hackathon.constant.TeamRequestMemberStatus;
import com.hacof.hackathon.constant.TeamRequestStatus;
import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.dto.TeamRequestSearchDTO;

public interface TeamRequestService {
    TeamRequestDTO createTeamRequest(TeamRequestDTO request);

    TeamRequestDTO updateMemberResponse(String requestId, String userId, TeamRequestMemberStatus status, String note);

    TeamRequestDTO reviewTeamRequest(String requestId, TeamRequestStatus status, String note);

    Page<TeamRequestDTO> searchTeamRequests(TeamRequestSearchDTO searchDTO);
}
