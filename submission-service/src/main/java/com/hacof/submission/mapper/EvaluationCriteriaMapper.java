package com.hacof.submission.mapper;

import com.hacof.submission.dtos.request.EvaluationCriteriaRequestDTO;
import com.hacof.submission.dtos.response.EvaluationCriteriaResponseDTO;
import com.hacof.submission.entities.EvaluationCriteria;
import org.springframework.stereotype.Component;

@Component
public class EvaluationCriteriaMapper {

    public EvaluationCriteria toEntity(EvaluationCriteriaRequestDTO requestDTO) {
        EvaluationCriteria criteria = new EvaluationCriteria();
        criteria.setName(requestDTO.getName());
        criteria.setDescription(requestDTO.getDescription());
        criteria.setMaxScore(requestDTO.getMaxScore());
        criteria.setWeight(requestDTO.getWeight());
        return criteria;
    }

    public EvaluationCriteriaResponseDTO toResponseDTO(EvaluationCriteria entity) {
        return new EvaluationCriteriaResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getMaxScore(),
                entity.getWeight(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getLastUpdatedBy(),
                entity.getLastUpdatedAt(),
                entity.getDeletedAt()
        );
    }
}
