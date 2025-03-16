package com.hacof.submission.service;

import java.util.List;
import java.util.Optional;

import com.hacof.submission.dto.request.RoundMarkCriterionRequestDTO;
import com.hacof.submission.dto.response.RoundMarkCriterionResponseDTO;

public interface RoundMarkCriterionService {

    List<RoundMarkCriterionResponseDTO> getAll();

    Optional<RoundMarkCriterionResponseDTO> getById(Long id);

    RoundMarkCriterionResponseDTO create(RoundMarkCriterionRequestDTO roundMarkCriterionDTO);

    RoundMarkCriterionResponseDTO update(Long id, RoundMarkCriterionRequestDTO updatedRoundMarkCriterionDTO);

    void delete(Long id);
}
