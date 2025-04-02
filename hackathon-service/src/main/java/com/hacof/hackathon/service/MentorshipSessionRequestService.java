package com.hacof.hackathon.service;

import java.time.LocalDateTime;
import java.util.List;

import com.hacof.hackathon.dto.MentorshipSessionRequestDTO;

public interface MentorshipSessionRequestService {
    MentorshipSessionRequestDTO create(MentorshipSessionRequestDTO mentorshipSessionRequestDTO);
    MentorshipSessionRequestDTO update(String id, MentorshipSessionRequestDTO mentorshipSessionRequestDTO);
    void delete(String id);
    List<MentorshipSessionRequestDTO> getAll();
    MentorshipSessionRequestDTO getById(String id);
    List<MentorshipSessionRequestDTO> getAllByMentorTeamId(String mentorTeamId);
}
