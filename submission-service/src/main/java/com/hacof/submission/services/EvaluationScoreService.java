package com.hacof.submission.services;

import java.util.List;

import com.hacof.submission.dtos.request.EvaluationScoreRequestDTO;
import com.hacof.submission.dtos.response.EvaluationScoreResponseDTO;

public interface EvaluationScoreService {
    List<EvaluationScoreResponseDTO> getAllScores();

    EvaluationScoreResponseDTO getScoreById(Integer id);

    EvaluationScoreResponseDTO createScore(EvaluationScoreRequestDTO score);

    EvaluationScoreResponseDTO updateScore(Integer id, EvaluationScoreRequestDTO scoreDetails);

    boolean deleteScore(Integer id);
}
