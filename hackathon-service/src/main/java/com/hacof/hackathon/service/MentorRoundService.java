package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.MentorRoundDTO;

public interface MentorRoundService {
    MentorRoundDTO assignMentorToRound(Long mentorId, Long roundId);

    List<MentorRoundDTO> getMentorsByRound(Long roundId);
}
