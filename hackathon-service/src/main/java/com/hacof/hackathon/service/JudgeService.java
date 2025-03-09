package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.JudgeDTO;

public interface JudgeService {
    List<JudgeDTO> getAllJudges();

    JudgeDTO getJudgeById(Long id);

    JudgeDTO createJudge(JudgeDTO judgeDTO);

    JudgeDTO updateJudge(Long id, JudgeDTO judgeDTO);

    void deleteJudge(Long id);

    void assignJudgeToRound(Long judgeId, Long roundId);
}
