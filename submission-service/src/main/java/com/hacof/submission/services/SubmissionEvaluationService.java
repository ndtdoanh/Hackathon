package com.hacof.submission.services;

import com.hacof.submission.dtos.request.SubmissionEvaluationRequestDTO;
import com.hacof.submission.dtos.response.SubmissionEvaluationResponseDTO;

import java.util.List;

public interface SubmissionEvaluationService {
    List<SubmissionEvaluationResponseDTO> getAllEvaluations();
    SubmissionEvaluationResponseDTO getEvaluationById(Long id);
    SubmissionEvaluationResponseDTO createEvaluation(SubmissionEvaluationRequestDTO evaluationRequestDTO);
    SubmissionEvaluationResponseDTO updateEvaluation(Long id, SubmissionEvaluationRequestDTO evaluationRequestDTO);
    boolean deleteEvaluation(Long id);
}
