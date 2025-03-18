package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.MentorshipRequestDTO;

public interface MentorshipRequestService {
    MentorshipRequestDTO requestMentor(Long teamId, Long mentorId);

    MentorshipRequestDTO approveRequest(Long requestId, Long mentorId);

    MentorshipRequestDTO rejectRequest(Long requestId, Long mentorId);

    List<MentorshipRequestDTO> getRequestsByTeam(Long teamId);
}
