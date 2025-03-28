package com.hacof.submission.dto.response;

import com.hacof.submission.entity.JudgeSubmissionDetail;
import com.hacof.submission.entity.RoundMarkCriterion;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class JudgeSubmissionDetailResponseDTO {

    private Long id;
    private Integer score;
    private String note;
    private Long judgeSubmissionId;
    private RoundMarkCriterionResponseDTO roundMarkCriterion;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    // Constructor
    public JudgeSubmissionDetailResponseDTO(JudgeSubmissionDetail entity) {
        this.id = entity.getId();
        this.score = entity.getScore();
        this.note = entity.getNote();
        this.createdDate = entity.getCreatedDate();
        this.lastModifiedDate = entity.getLastModifiedDate();

        if (entity.getJudgeSubmission() != null) {
            this.judgeSubmissionId = entity.getJudgeSubmission().getId();  // Fetch judgeSubmissionId
        }

        if (entity.getRoundMarkCriterion() != null) {
            this.roundMarkCriterion = new RoundMarkCriterionResponseDTO(
                    entity.getRoundMarkCriterion().getId(),
                    entity.getRoundMarkCriterion().getName(),
                    entity.getRoundMarkCriterion().getMaxScore(),
                    entity.getRoundMarkCriterion().getNote(),
                    entity.getRoundMarkCriterion().getCreatedBy() != null ? entity.getRoundMarkCriterion().getCreatedBy().getUsername() : null,
                    entity.getRoundMarkCriterion().getCreatedDate(),
                    entity.getRoundMarkCriterion().getLastModifiedDate(),
                    null
            );
        }
    }
}
