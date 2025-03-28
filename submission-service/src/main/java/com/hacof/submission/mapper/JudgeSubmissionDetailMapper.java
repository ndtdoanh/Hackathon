package com.hacof.submission.mapper;

import com.hacof.submission.dto.response.JudgeSubmissionResponseDTO;
import com.hacof.submission.dto.response.RoundMarkCriterionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hacof.submission.dto.request.JudgeSubmissionDetailRequestDTO;
import com.hacof.submission.dto.response.JudgeSubmissionDetailResponseDTO;
import com.hacof.submission.entity.JudgeSubmission;
import com.hacof.submission.entity.JudgeSubmissionDetail;
import com.hacof.submission.entity.RoundMarkCriterion;
import com.hacof.submission.repository.JudgeSubmissionRepository;
import com.hacof.submission.repository.RoundMarkCriterionRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JudgeSubmissionDetailMapper {

    @Autowired
    private JudgeSubmissionRepository judgeSubmissionRepository;

    @Autowired
    private RoundMarkCriterionRepository roundMarkCriterionRepository;

    public JudgeSubmissionDetail toEntity(JudgeSubmissionDetailRequestDTO dto) {
        JudgeSubmissionDetail entity = new JudgeSubmissionDetail();

        entity.setScore(dto.getScore());
        entity.setNote(dto.getNote());

        Optional<JudgeSubmission> judgeSubmission = judgeSubmissionRepository.findById(dto.getJudgeSubmissionId());
        if (judgeSubmission.isPresent()) {
            entity.setJudgeSubmission(judgeSubmission.get());
        } else {
            throw new IllegalArgumentException("Judge Submission not found with ID " + dto.getJudgeSubmissionId());
        }

        Optional<RoundMarkCriterion> roundMarkCriterion =
                roundMarkCriterionRepository.findById(dto.getRoundMarkCriterionId());
        if (roundMarkCriterion.isPresent()) {
            entity.setRoundMarkCriterion(roundMarkCriterion.get());
        } else {
            throw new IllegalArgumentException(
                    "Round Mark Criterion not found with ID " + dto.getRoundMarkCriterionId());
        }

        return entity;
    }

    public static JudgeSubmissionDetailResponseDTO toResponseDTO(JudgeSubmissionDetail entity) {
        JudgeSubmissionDetailResponseDTO responseDTO = new JudgeSubmissionDetailResponseDTO(entity);
        responseDTO.setId(entity.getId());
        responseDTO.setScore(entity.getScore());
        responseDTO.setNote(entity.getNote());

        JudgeSubmissionResponseDTO judgeSubmissionResponseDTO = new JudgeSubmissionResponseDTO(entity.getJudgeSubmission()); // Sử dụng constructor có tham số
        responseDTO.setJudgeSubmissionId(judgeSubmissionResponseDTO.getId()); // Set the ID directly in the response DTO

        RoundMarkCriterionResponseDTO roundMarkCriterionResponseDTO = new RoundMarkCriterionResponseDTO(
                entity.getRoundMarkCriterion().getId(),
                entity.getRoundMarkCriterion().getName(),
                entity.getRoundMarkCriterion().getMaxScore(),
                entity.getRoundMarkCriterion().getNote(),
                entity.getRoundMarkCriterion().getCreatedBy() != null ? entity.getRoundMarkCriterion().getCreatedBy().getUsername() : null,
                entity.getRoundMarkCriterion().getCreatedDate(),
                entity.getRoundMarkCriterion().getLastModifiedDate(),
                null
        );
        responseDTO.setRoundMarkCriterion(roundMarkCriterionResponseDTO);

        responseDTO.setCreatedDate(entity.getCreatedDate());
        responseDTO.setLastModifiedDate(entity.getLastModifiedDate());

        return responseDTO;
    }

    public static List<JudgeSubmissionDetailResponseDTO> toResponseDTOList(List<JudgeSubmissionDetail> entities) {
        return entities.stream().map(JudgeSubmissionDetailMapper::toResponseDTO).collect(Collectors.toList());
    }
}
