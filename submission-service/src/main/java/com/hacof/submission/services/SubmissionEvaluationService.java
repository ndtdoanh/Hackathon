package com.hacof.submission.services;

import java.util.List;

import com.hacof.submission.dtos.request.SubmissionEvaluationRequestDTO;
import com.hacof.submission.dtos.response.SubmissionEvaluationResponseDTO;

public interface SubmissionEvaluationService {
    List<SubmissionEvaluationResponseDTO> getAllEvaluations();

    SubmissionEvaluationResponseDTO getEvaluationById(Long id);

    SubmissionEvaluationResponseDTO createEvaluation(SubmissionEvaluationRequestDTO evaluationRequestDTO);

    SubmissionEvaluationResponseDTO updateEvaluation(Long id, SubmissionEvaluationRequestDTO evaluationRequestDTO);

    boolean deleteEvaluation(Long id);
}
