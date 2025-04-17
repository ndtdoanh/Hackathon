package com.hacof.submission.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JudgeSubmissionRequestDTO {
    private Long judgeId;
    private Long submissionId;
    private int score;
    private String note;
    private List<JudgeSubmissionDetailRequestDTO> judgeSubmissionDetails;
}
