package com.hacof.submission.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class JudgeSubmissionDetailResponseDTO {

    private Long id;
    private Long judgeSubmissionId;
    private Long roundMarkCriterionId;
    private Integer score;
    private String note;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
