package com.hacof.submission.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluationScoreRequestDTO {
    private Long submissionId;
    private Integer evaluationCriteriaId;
    private Long judgeId;
    private Integer score;
    private String comment;
}
