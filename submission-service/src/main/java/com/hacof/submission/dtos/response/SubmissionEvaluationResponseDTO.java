package com.hacof.submission.dtos.response;

import com.hacof.submission.entities.Submissionevaluation;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class SubmissionEvaluationResponseDTO {

    private Long id;
    private Long submissionId;
    private Long judgeId;
    private Float score;
    private String feedback;
    private Instant evaluatedAt;
    private Instant createdAt;
    private String createdBy;
    private String updatedBy;
    private Instant updatedAt;

    public SubmissionEvaluationResponseDTO() {
    }

    public SubmissionEvaluationResponseDTO(Submissionevaluation submissionevaluation) {
        this.id = submissionevaluation.getId();
        this.submissionId = submissionevaluation.getSubmission() != null ? submissionevaluation.getSubmission().getId() : null;
        this.judgeId = submissionevaluation.getJudge() != null ? submissionevaluation.getJudge().getId() : null;
        this.score = submissionevaluation.getScore();
        this.feedback = submissionevaluation.getFeedback();
        this.evaluatedAt = submissionevaluation.getEvaluatedAt();
        this.createdAt = submissionevaluation.getCreatedAt();
        this.createdBy = submissionevaluation.getCreatedBy();
        this.updatedBy = submissionevaluation.getUpdatedBy();
        this.updatedAt = submissionevaluation.getUpdatedAt();
    }
}
