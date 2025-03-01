package com.hacof.submission.dtos.request;

import lombok.Data;

@Data
public class EvaluationCriteriaRequestDTO {

    private String name;

    private String description;

    private Integer maxScore;

    private Float weight;

    private Long hackathonId;  // Trường hackathonId cần được thêm vào
}
