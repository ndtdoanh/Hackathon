package com.hacof.submission.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RoundMarkCriterionResponseDTO {

    private Long id;
    private String name;
    private Integer maxScore;
    private String note;
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public RoundMarkCriterionResponseDTO(Long id, String name, Integer maxScore, String note,
                                         String createdBy, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.name = name;
        this.maxScore = maxScore;
        this.note = note;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
