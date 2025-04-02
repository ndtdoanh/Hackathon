package com.hacof.hackathon.service;

import java.time.LocalDateTime;
import java.util.List;

import com.hacof.hackathon.dto.MentorshipSessionRequestDTO;

public interface MentorshipSessionRequestService {
    MentorshipSessionRequestDTO requestSession(
            String mentorTeamId, LocalDateTime startTime, LocalDateTime endTime, String location, String description);

    MentorshipSessionRequestDTO approveSession(Long sessionId, Long mentorId);

    MentorshipSessionRequestDTO rejectSession(Long sessionId, Long mentorId);

    // List<MentorshipSessionRequestDTO> getSessionsByMentorTeam(Long mentorTeamId);
    List<MentorshipSessionRequestDTO> getAllByMentorTeamId(String mentorTeamId);
}
