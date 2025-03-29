package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.dto.TeamRequestDTO;

public interface TeamRequestService {
    TeamRequestDTO createTeamRequest(TeamRequestDTO teamRequestDTO);

    TeamRequestDTO approveTeamRequest(long id, long userId);

    TeamRequestDTO rejectTeamRequest(long id, long userId);

    TeamDTO createTeamFromRequest(long teamRequestId);

    void sendRequestToMembers(long teamRequestId);

    void confirmMemberRequest(long teamRequestId, long userId, String status);
}
