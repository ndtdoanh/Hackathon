package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.CompetitionRoundDTO;

public interface CompetitionRoundService {
    List<CompetitionRoundDTO> getAllRounds();

    CompetitionRoundDTO getRoundById(Long id);

    CompetitionRoundDTO createRound(CompetitionRoundDTO roundDTO);

    CompetitionRoundDTO updateRound(Long id, CompetitionRoundDTO roundDTO);

    void deleteRound(Long id);

    void assignJudgeToRound(Long roundId, Long judgeId);

    void assignTaskToMember(Long teamId, Long memberId, String task);
}
