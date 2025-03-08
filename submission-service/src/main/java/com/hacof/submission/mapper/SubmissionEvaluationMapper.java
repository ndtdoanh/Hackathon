package com.hacof.submission.mapper;

import com.hacof.submission.dtos.request.SubmissionEvaluationRequestDTO;
import com.hacof.submission.dtos.response.SubmissionEvaluationResponseDTO;
import com.hacof.submission.entities.Submissionevaluation;
import com.hacof.submission.entities.Submission;
import com.hacof.submission.entities.User;
import com.hacof.submission.repositories.SubmissionRepository;
import com.hacof.submission.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubmissionEvaluationMapper {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private UserRepository userRepository;

    // Chuyển DTO thành Entity
    public Submissionevaluation toEntity(SubmissionEvaluationRequestDTO dto) {
        Submissionevaluation entity = new Submissionevaluation();

        // Lấy Submission và Judge từ Repository
        Submission submission = submissionRepository.findById(dto.getSubmissionId())
                .orElseThrow(() -> new RuntimeException("Submission not found"));
        User judge = userRepository.findById(dto.getJudgeId())
                .orElseThrow(() -> new RuntimeException("Judge not found"));

        entity.setSubmission(submission);
        entity.setJudge(judge);
        entity.setFeedback(dto.getFeedback());
        entity.setEvaluatedAt(dto.getEvaluatedAt());

        // Không cần set điểm và trạng thái vì chúng sẽ được xử lý trong service
        return entity;
    }

    // Chuyển Entity thành DTO
    public SubmissionEvaluationResponseDTO toResponseDTO(Submissionevaluation entity) {
        SubmissionEvaluationResponseDTO dto = new SubmissionEvaluationResponseDTO();
        dto.setId(entity.getId());
        dto.setSubmissionId(entity.getSubmission() != null ? entity.getSubmission().getId() : null);
        dto.setJudgeId(entity.getJudge() != null ? entity.getJudge().getId() : null);
        dto.setScore(entity.getScore()); // Điểm sẽ được tính và gán trong service
        dto.setFeedback(entity.getFeedback());
        dto.setEvaluatedAt(entity.getEvaluatedAt());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    // Cập nhật thông tin Entity từ DTO
    public void updateEntityFromDTO(SubmissionEvaluationRequestDTO dto, Submissionevaluation entity) {
        if (dto.getFeedback() != null) {
            entity.setFeedback(dto.getFeedback());
        }
        if (dto.getEvaluatedAt() != null) {
            entity.setEvaluatedAt(dto.getEvaluatedAt());
        }
    }
}
