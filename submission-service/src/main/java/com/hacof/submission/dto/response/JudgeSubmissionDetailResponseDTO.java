package com.hacof.submission.dto.response;

import java.time.LocalDateTime;

import com.hacof.submission.entity.JudgeSubmissionDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JudgeSubmissionDetailResponseDTO {

    private Long id;
    private Long judgeSubmissionId;
    private Long roundMarkCriterionId;
    private Integer score;
    private String note;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    // Constructor yêu cầu JudgeSubmissionDetail entity
    public JudgeSubmissionDetailResponseDTO(JudgeSubmissionDetail entity) {
        this.id = entity.getId();
        this.judgeSubmissionId = entity.getJudgeSubmission() != null ? entity.getJudgeSubmission().getId() : null;
        this.roundMarkCriterionId = entity.getRoundMarkCriterion() != null ? entity.getRoundMarkCriterion().getId() : null;
        this.score = entity.getScore();
        this.note = entity.getNote();
        this.createdDate = entity.getCreatedDate();
        this.lastModifiedDate = entity.getLastModifiedDate();
    }
}
