package com.hacof.submission.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoundMarkCriterionRequestDTO {

    private String name;

    private Integer maxScore;

    private String note;

    private Long roundId;
}
