package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.MentorDTO;

import java.util.List;

public interface MentorService {
    List<MentorDTO> getAllMentors();

    MentorDTO getMentorById(Long id);

    MentorDTO createMentor(MentorDTO mentorDTO);

    MentorDTO updateMentor(Long id, MentorDTO mentorDTO);

    void deleteMentor(Long id);
}
