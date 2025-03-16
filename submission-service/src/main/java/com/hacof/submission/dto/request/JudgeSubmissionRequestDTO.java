package com.hacof.submission.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JudgeSubmissionRequestDTO {
    private Long judgeId;
    private Long submissionId;
    private String note;
}
