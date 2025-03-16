package com.hacof.submission.dto.request;

import lombok.Data;

@Data
public class RoundMarkCriterionRequestDTO {

    private String name;

    private Integer maxScore;

    private String note;

    private Long roundId;
}
