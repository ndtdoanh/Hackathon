package com.hacof.submission.dto.response;

import java.time.LocalDateTime;

import com.hacof.submission.entity.JudgeSubmissionDetail;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JudgeSubmissionDetailResponseDTO {

    private Long id;
    private Integer score;
    private String note;
    private Long judgeSubmissionId;
    private RoundMarkCriterionResponseDTO roundMarkCriterion;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    // Constructor
    public JudgeSubmissionDetailResponseDTO(JudgeSubmissionDetail entity) {
        this.id = entity.getId();
        this.score = entity.getScore();
        this.note = entity.getNote();
        this.createdDate = entity.getCreatedDate();
        this.lastModifiedDate = entity.getLastModifiedDate();

        if (entity.getJudgeSubmission() != null) {
            this.judgeSubmissionId = entity.getJudgeSubmission().getId(); // Fetch judgeSubmissionId
        }

        if (entity.getRoundMarkCriterion() != null) {
            this.roundMarkCriterion = RoundMarkCriterionResponseDTO.builder()
                    .id(entity.getRoundMarkCriterion().getId())
                    .name(entity.getRoundMarkCriterion().getName())
                    .maxScore(entity.getRoundMarkCriterion().getMaxScore())
                    .note(entity.getRoundMarkCriterion().getNote())
                    .createdBy(
                            entity.getRoundMarkCriterion().getCreatedBy() != null
                                    ? entity.getRoundMarkCriterion()
                                            .getCreatedBy()
                                            .getUsername()
                                    : null)
                    .createdDate(entity.getRoundMarkCriterion().getCreatedDate())
                    .lastModifiedDate(entity.getRoundMarkCriterion().getLastModifiedDate())
                    .judgeSubmissionDetails(null) // Avoid passing `null` directly
                    .build();
        }
    }
}
