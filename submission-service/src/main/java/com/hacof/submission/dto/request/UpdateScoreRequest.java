package com.hacof.submission.dto.request;

import lombok.Data;

@Data
public class UpdateScoreRequest {

    private Long judgeSubmissionId;
    private String note;
}
