package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.MentorshipSessionRequestDTO;

public interface MentorshipSessionRequestService {
    MentorshipSessionRequestDTO createSessionRequest(Long mentorshipRequestId, String sessionDetails);

    MentorshipSessionRequestDTO approveSession(Long sessionId, Long mentorId);

    MentorshipSessionRequestDTO rejectSession(Long sessionId, Long mentorId);

    List<MentorshipSessionRequestDTO> getSessionsByMentorshipRequest(Long mentorshipRequestId);
}
