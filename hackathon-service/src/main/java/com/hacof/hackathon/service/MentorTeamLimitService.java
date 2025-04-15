package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.MentorTeamLimitDTO;

import java.util.List;

public interface MentorTeamLimitService {
    MentorTeamLimitDTO create(MentorTeamLimitDTO mentorTeamLimitDTO);

    MentorTeamLimitDTO update(Long id, MentorTeamLimitDTO mentorTeamLimitDTO);

    void delete(Long id);

    List<MentorTeamLimitDTO> getAll();

    MentorTeamLimitDTO getById(Long id);
}
