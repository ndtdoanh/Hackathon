package com.hacof.submission.dtos.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluationScoreResponseDTO {
    private Integer id;
    private Long submissionId;
    private Integer evaluationCriteriaId;
    private Long judgeId;
    private Integer score;
    private String comment;
    private String createdBy;
    private LocalDateTime createdAt;
    private String lastUpdatedBy;
    private LocalDateTime lastUpdatedAt;
    private LocalDateTime deletedAt;
}
