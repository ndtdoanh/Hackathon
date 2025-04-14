package com.hacof.submission.service;

import com.hacof.submission.dto.request.JudgeRoundRequestDTO;
import com.hacof.submission.dto.response.JudgeRoundResponseDTO;

import java.util.List;

public interface JudgeRoundService {
    JudgeRoundResponseDTO createJudgeRound(JudgeRoundRequestDTO dto);

    JudgeRoundResponseDTO updateJudgeRound(Long id, JudgeRoundRequestDTO dto);

    boolean deleteJudgeRound(Long id);

    JudgeRoundResponseDTO getJudgeRound(Long id);

    List<JudgeRoundResponseDTO> getAllJudgeRounds();

    JudgeRoundResponseDTO updateJudgeRoundByJudgeId(Long judgeId, JudgeRoundRequestDTO dto);

    List<JudgeRoundResponseDTO> getJudgeRoundsByRoundId(Long roundId);

    void deleteJudgeRoundByJudgeIdAndRoundId(Long judgeId, Long roundId);
}
