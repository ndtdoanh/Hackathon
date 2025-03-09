package com.hacof.submission.services;

import com.hacof.submission.dtos.request.EvaluationCriteriaRequestDTO;
import com.hacof.submission.dtos.response.EvaluationCriteriaResponseDTO;
import com.hacof.submission.entities.EvaluationCriteria;

import java.util.List;
import java.util.Optional;

public interface EvaluationCriteriaService {
    List<EvaluationCriteriaResponseDTO> getAll();

    Optional<EvaluationCriteriaResponseDTO> getById(Integer id);

    EvaluationCriteriaResponseDTO create(EvaluationCriteriaRequestDTO criteriaDTO);

    EvaluationCriteriaResponseDTO update(Integer id, EvaluationCriteriaRequestDTO updatedCriteriaDTO);

    void delete(Integer id);
}
