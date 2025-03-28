package com.hacof.submission.mapper;

import com.hacof.submission.dto.request.RoundMarkCriterionRequestDTO;
import com.hacof.submission.dto.response.JudgeSubmissionDetailResponseDTO;
import com.hacof.submission.dto.response.RoundMarkCriterionResponseDTO;
import com.hacof.submission.entity.RoundMarkCriterion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoundMarkCriterionMapper {

    public RoundMarkCriterion toEntity(RoundMarkCriterionRequestDTO requestDTO) {
        RoundMarkCriterion criterion = new RoundMarkCriterion();
        criterion.setName(requestDTO.getName());
        criterion.setMaxScore(requestDTO.getMaxScore());
        criterion.setNote(requestDTO.getNote());
        return criterion;
    }

    public RoundMarkCriterionResponseDTO toResponseDTO(RoundMarkCriterion entity) {
        RoundMarkCriterionResponseDTO responseDTO = new RoundMarkCriterionResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setMaxScore(entity.getMaxScore());
        responseDTO.setNote(entity.getNote());
        responseDTO.setCreatedBy(entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        responseDTO.setCreatedDate(entity.getCreatedDate());
        responseDTO.setLastModifiedDate(entity.getLastModifiedDate());

        if (entity.getJudgeSubmissionDetails() != null) {
            List<JudgeSubmissionDetailResponseDTO> judgeSubmissionDetails = entity.getJudgeSubmissionDetails().stream()
                    .map(JudgeSubmissionDetailResponseDTO::new)
                    .collect(Collectors.toList());
            responseDTO.setJudgeSubmissionDetails(judgeSubmissionDetails);
        }

        return responseDTO;
    }
}
