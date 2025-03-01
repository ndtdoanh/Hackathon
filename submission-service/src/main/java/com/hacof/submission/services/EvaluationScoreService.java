package com.hacof.submission.services;

import com.hacof.submission.dtos.request.EvaluationScoreRequestDTO;
import com.hacof.submission.dtos.response.EvaluationScoreResponseDTO;
import com.hacof.submission.entities.EvaluationScores;

import java.util.List;

public interface EvaluationScoreService {
    List<EvaluationScoreResponseDTO> getAllScores();
    EvaluationScoreResponseDTO getScoreById(Integer id);
    EvaluationScoreResponseDTO createScore(EvaluationScoreRequestDTO score);
    EvaluationScoreResponseDTO updateScore(Integer id, EvaluationScoreRequestDTO scoreDetails);
    boolean deleteScore(Integer id);
}

