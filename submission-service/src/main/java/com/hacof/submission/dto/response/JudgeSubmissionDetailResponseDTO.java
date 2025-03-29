package com.hacof.submission.dto.response;

import java.time.LocalDateTime;

import com.hacof.submission.entity.JudgeSubmissionDetail;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class JudgeSubmissionDetailResponseDTO {
    String id;
    Integer score;
    String note;
    Long judgeSubmissionId;
    RoundMarkCriterionResponseDTO roundMarkCriterion;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;

    public JudgeSubmissionDetailResponseDTO(JudgeSubmissionDetail entity) {
        this.id = String.valueOf(entity.getId());
        this.score = entity.getScore();
        this.note = entity.getNote();
        this.createdDate = entity.getCreatedDate();
        this.lastModifiedDate = entity.getLastModifiedDate();

        if (entity.getJudgeSubmission() != null) {
            this.judgeSubmissionId = entity.getJudgeSubmission().getId();
        }

        if (entity.getRoundMarkCriterion() != null) {
            this.roundMarkCriterion = RoundMarkCriterionResponseDTO.builder()
                    .id(String.valueOf(entity.getRoundMarkCriterion().getId()))
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
                    .judgeSubmissionDetails(null)
                    .build();
        }
    }
}
