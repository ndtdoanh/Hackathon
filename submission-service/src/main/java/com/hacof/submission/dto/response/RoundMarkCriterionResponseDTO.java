package com.hacof.submission.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RoundMarkCriterionResponseDTO {
    private Long id;
    private String name;
    private int maxScore;
    private String note;
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private List<JudgeSubmissionDetailResponseDTO> judgeSubmissionDetails;

    public RoundMarkCriterionResponseDTO() {
    }

    public RoundMarkCriterionResponseDTO(Long id, String name, int maxScore, String note, String createdBy,
                                         LocalDateTime createdDate, LocalDateTime lastModifiedDate,
                                         List<JudgeSubmissionDetailResponseDTO> judgeSubmissionDetails) {
        this.id = id;
        this.name = name;
        this.maxScore = maxScore;
        this.note = note;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.judgeSubmissionDetails = judgeSubmissionDetails;
    }
}
