package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.MentorDTO;

public interface MentorService {
    List<MentorDTO> getAllMentors();

    MentorDTO getMentorById(Long id);

    MentorDTO createMentor(MentorDTO mentorDTO);

    MentorDTO updateMentor(Long id, MentorDTO mentorDTO);

    void deleteMentor(Long id);
}
