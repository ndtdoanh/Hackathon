package com.hacof.submission.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hacof.submission.dto.request.JudgeSubmissionDetailRequestDTO;
import com.hacof.submission.dto.response.JudgeSubmissionDetailResponseDTO;
import com.hacof.submission.entity.JudgeSubmissionDetail;
import com.hacof.submission.entity.JudgeSubmission;
import com.hacof.submission.entity.RoundMarkCriterion;
import com.hacof.submission.repository.JudgeSubmissionRepository;
import com.hacof.submission.repository.RoundMarkCriterionRepository;

import java.util.Optional;

@Component
public class JudgeSubmissionDetailMapper {

    @Autowired
    private JudgeSubmissionRepository judgeSubmissionRepository;

    @Autowired
    private RoundMarkCriterionRepository roundMarkCriterionRepository;

    public JudgeSubmissionDetail toEntity(JudgeSubmissionDetailRequestDTO dto) {
        JudgeSubmissionDetail entity = new JudgeSubmissionDetail();

        // Thiết lập các trường từ DTO
        entity.setScore(dto.getScore());
        entity.setNote(dto.getNote());

        // Ánh xạ đối tượng judgeSubmission và roundMarkCriterion từ ID
        Optional<JudgeSubmission> judgeSubmission = judgeSubmissionRepository.findById(dto.getJudgeSubmissionId());
        if (judgeSubmission.isPresent()) {
            entity.setJudgeSubmission(judgeSubmission.get());
        } else {
            throw new IllegalArgumentException("Judge Submission not found with ID " + dto.getJudgeSubmissionId());
        }

        Optional<RoundMarkCriterion> roundMarkCriterion = roundMarkCriterionRepository.findById(dto.getRoundMarkCriterionId());
        if (roundMarkCriterion.isPresent()) {
            entity.setRoundMarkCriterion(roundMarkCriterion.get());
        } else {
            throw new IllegalArgumentException("Round Mark Criterion not found with ID " + dto.getRoundMarkCriterionId());
        }

        return entity;
    }

    public JudgeSubmissionDetailResponseDTO toResponseDTO(JudgeSubmissionDetail entity) {
        JudgeSubmissionDetailResponseDTO dto = new JudgeSubmissionDetailResponseDTO();

        // Thiết lập các trường trong ResponseDTO
        dto.setId(entity.getId());
        dto.setJudgeSubmissionId(entity.getJudgeSubmission() != null ? entity.getJudgeSubmission().getId() : null);
        dto.setRoundMarkCriterionId(entity.getRoundMarkCriterion() != null ? entity.getRoundMarkCriterion().getId() : null);
        dto.setScore(entity.getScore());
        dto.setNote(entity.getNote());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastModifiedDate(entity.getLastModifiedDate());

        return dto;
    }
}
