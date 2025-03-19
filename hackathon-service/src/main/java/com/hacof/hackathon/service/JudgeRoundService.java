package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.JudgeRoundDTO;

public interface JudgeRoundService {
    JudgeRoundDTO assignJudgeToRound(Long judgeId, Long roundId);
}
