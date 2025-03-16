package com.hacof.submission.dto.response;

import lombok.Data;

@Data
public class JudgeRoundResponseDTO {
    private Long id;
    private Long judgeId;
    private Long roundId;
    private boolean isDeleted;
    private String createdDate;
    private String lastModifiedDate;
}
