package com.hacof.submission.service;

import java.util.List;

import com.hacof.submission.dto.request.JudgeRoundRequestDTO;
import com.hacof.submission.dto.response.JudgeRoundResponseDTO;

public interface JudgeRoundService {
    JudgeRoundResponseDTO createJudgeRound(JudgeRoundRequestDTO dto);

    JudgeRoundResponseDTO updateJudgeRound(Long id, JudgeRoundRequestDTO dto);

    boolean deleteJudgeRound(Long id);

    JudgeRoundResponseDTO getJudgeRound(Long id);

    List<JudgeRoundResponseDTO> getAllJudgeRounds();

    JudgeRoundResponseDTO updateJudgeRoundByJudgeId(Long judgeId, JudgeRoundRequestDTO dto);

    List<JudgeRoundResponseDTO> getJudgeRoundsByRoundId(Long roundId);
}
