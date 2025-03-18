package com.hacof.submission.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JudgeSubmissionDetailRequestDTO {

    private Long judgeSubmissionId;
    private Long roundMarkCriterionId;
    private Integer score;
    private String note;
}
