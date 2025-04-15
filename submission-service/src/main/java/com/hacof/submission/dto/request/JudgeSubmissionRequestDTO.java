package com.hacof.submission.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JudgeSubmissionRequestDTO {
    private Long judgeId;
    private Long submissionId;
    private int score;
    private String note;
    private List<JudgeSubmissionDetailRequestDTO> judgeSubmissionDetails;
}
