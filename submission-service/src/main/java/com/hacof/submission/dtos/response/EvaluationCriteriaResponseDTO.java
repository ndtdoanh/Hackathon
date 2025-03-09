package com.hacof.submission.dtos.response;

import lombok.Data;

import java.time.Instant;

@Data
public class EvaluationCriteriaResponseDTO {

    private Integer id;
    private String name;
    private String description;
    private Integer maxScore;
    private Float weight;
    private String createdBy;
    private Instant createdAt;
    private String lastUpdatedBy;
    private Instant lastUpdatedAt;
    private Instant deletedAt;

    // Constructor from Entity
    public EvaluationCriteriaResponseDTO(Integer id, String name, String description, Integer maxScore, Float weight,
                                         String createdBy, Instant createdAt, String lastUpdatedBy, Instant lastUpdatedAt, Instant deletedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.maxScore = maxScore;
        this.weight = weight;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedAt = lastUpdatedAt;
        this.deletedAt = deletedAt;
    }
}
