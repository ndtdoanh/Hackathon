package com.hacof.hackathon.service;

import com.hacof.hackathon.entity.TeamRequest;
import com.hacof.hackathon.entity.TeamRequestMember;

public interface NotificationService {
    void notifyTeamRequestCreated(TeamRequest request);

    void notifyMemberResponse(TeamRequestMember member);

    void notifyTeamRequestReviewed(TeamRequest request);
}
