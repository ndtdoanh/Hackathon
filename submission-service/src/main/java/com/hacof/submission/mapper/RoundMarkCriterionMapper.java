package com.hacof.submission.mapper;

import org.springframework.stereotype.Component;

import com.hacof.submission.dto.request.RoundMarkCriterionRequestDTO;
import com.hacof.submission.dto.response.RoundMarkCriterionResponseDTO;
import com.hacof.submission.entity.RoundMarkCriterion;

@Component
public class RoundMarkCriterionMapper {

    // Chuyển đổi từ RoundMarkCriterionRequestDTO sang RoundMarkCriterion entity
    public RoundMarkCriterion toEntity(RoundMarkCriterionRequestDTO requestDTO) {
        RoundMarkCriterion criterion = new RoundMarkCriterion();
        criterion.setName(requestDTO.getName());
        criterion.setMaxScore(requestDTO.getMaxScore());
        criterion.setNote(requestDTO.getNote());
        return criterion;
    }

    // Chuyển đổi từ RoundMarkCriterion entity sang RoundMarkCriterionResponseDTO
    public RoundMarkCriterionResponseDTO toResponseDTO(RoundMarkCriterion entity) {
        return new RoundMarkCriterionResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getMaxScore(),
                entity.getNote(),
                entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null,
                entity.getCreatedDate(),
                entity.getLastModifiedDate()
        );
    }
}
