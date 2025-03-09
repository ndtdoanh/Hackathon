package com.hacof.submission.mapper;

import com.hacof.submission.dtos.request.EvaluationScoreRequestDTO;
import com.hacof.submission.dtos.response.EvaluationScoreResponseDTO;
import com.hacof.submission.entities.EvaluationScores;
import com.hacof.submission.entities.Submission;
import com.hacof.submission.entities.EvaluationCriteria;
import com.hacof.submission.entities.User;
import com.hacof.submission.repositories.SubmissionRepository;
import com.hacof.submission.repositories.EvaluationCriteriaRepository;
import com.hacof.submission.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Component
public class EvaluationScoreMapper {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private EvaluationCriteriaRepository evaluationCriteriaRepository;

    @Autowired
    private UserRepository userRepository;

    public EvaluationScores toEntity(EvaluationScoreRequestDTO dto) {
        EvaluationScores entity = new EvaluationScores();

        // Thiết lập các trường từ DTO
        entity.setScore(dto.getScore());
        entity.setComment(dto.getComment());

        // Ánh xạ đối tượng submission, evaluationCriteria, và judge từ ID
        Optional<Submission> submission = submissionRepository.findById(dto.getSubmissionId());
        if (submission.isPresent()) {
            entity.setSubmission(submission.get());
        } else {
            throw new IllegalArgumentException("Submission not found with ID " + dto.getSubmissionId());
        }

        Optional<EvaluationCriteria> criteria = evaluationCriteriaRepository.findById(dto.getEvaluationCriteriaId());
        if (criteria.isPresent()) {
            entity.setEvaluationCriteria(criteria.get());
        } else {
            throw new IllegalArgumentException("EvaluationCriteria not found with ID " + dto.getEvaluationCriteriaId());
        }

        Optional<User> judge = userRepository.findById(dto.getJudgeId());
        if (judge.isPresent()) {
            entity.setJudge(judge.get());
        } else {
            throw new IllegalArgumentException("Judge not found with ID " + dto.getJudgeId());
        }

        return entity;
    }

    public EvaluationScoreResponseDTO toResponseDTO(EvaluationScores entity) {
        EvaluationScoreResponseDTO dto = new EvaluationScoreResponseDTO();

        // Thiết lập các trường trong ResponseDTO
        dto.setId(entity.getId());
        dto.setSubmissionId(entity.getSubmission() != null ? entity.getSubmission().getId() : null);
        dto.setEvaluationCriteriaId(entity.getEvaluationCriteria() != null ? entity.getEvaluationCriteria().getId() : null);
        dto.setJudgeId(entity.getJudge() != null ? entity.getJudge().getId() : null);
        dto.setScore(entity.getScore());
        dto.setComment(entity.getComment());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(convertInstantToLocalDateTime(entity.getCreatedAt()));
        dto.setLastUpdatedBy(entity.getLastUpdatedBy());
        dto.setLastUpdatedAt(convertInstantToLocalDateTime(entity.getLastUpdatedAt()));
        dto.setDeletedAt(convertInstantToLocalDateTime(entity.getDeletedAt()));

        return dto;
    }

    private LocalDateTime convertInstantToLocalDateTime(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneId.systemDefault()) : null;
    }
}
