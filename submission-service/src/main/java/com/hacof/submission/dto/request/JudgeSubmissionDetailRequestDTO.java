package com.hacof.submission.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JudgeSubmissionDetailRequestDTO {
    private Long roundMarkCriterionId;
    private Integer score;
    private String note;
}
