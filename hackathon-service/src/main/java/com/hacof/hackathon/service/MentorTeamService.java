package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.MentorTeamDTO;

import java.util.List;

public interface MentorTeamService {
    MentorTeamDTO create(MentorTeamDTO mentorTeamDTO);

    MentorTeamDTO update(String id, MentorTeamDTO mentorTeamDTO);

    void delete(String id);

    List<MentorTeamDTO> getAllByHackathonIdAndTeamId(String hackathonId, String teamId);

    List<MentorTeamDTO> getAllByMentorId(String mentorId);
}
