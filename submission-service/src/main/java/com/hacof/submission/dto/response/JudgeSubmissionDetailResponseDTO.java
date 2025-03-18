package com.hacof.submission.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

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
