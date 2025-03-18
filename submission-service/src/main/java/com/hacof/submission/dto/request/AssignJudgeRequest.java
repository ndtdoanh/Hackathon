package com.hacof.submission.dto.request;

import lombok.Data;

@Data
public class AssignJudgeRequest {

    private Long submissionId;
    private Long judgeId;
}
