package com.hacof.submission.services;

import java.util.List;
import java.util.Optional;

import com.hacof.submission.dtos.request.EvaluationCriteriaRequestDTO;
import com.hacof.submission.dtos.response.EvaluationCriteriaResponseDTO;

public interface EvaluationCriteriaService {
    List<EvaluationCriteriaResponseDTO> getAll();

    Optional<EvaluationCriteriaResponseDTO> getById(Integer id);

    EvaluationCriteriaResponseDTO create(EvaluationCriteriaRequestDTO criteriaDTO);

    EvaluationCriteriaResponseDTO update(Integer id, EvaluationCriteriaRequestDTO updatedCriteriaDTO);

    void delete(Integer id);
}
