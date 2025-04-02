package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.MentorshipRequestDTO;

public interface MentorshipRequestService {
    MentorshipRequestDTO create(MentorshipRequestDTO mentorshipRequestDTO);

    MentorshipRequestDTO update(Long id, MentorshipRequestDTO mentorshipRequestDTO);

    void delete(Long id);

    List<MentorshipRequestDTO> getAll();

    MentorshipRequestDTO getById(Long id);
}
