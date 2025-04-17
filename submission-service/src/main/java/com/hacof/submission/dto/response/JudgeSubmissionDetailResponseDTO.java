package com.hacof.submission.dto.response;

import java.time.LocalDateTime;

import com.hacof.submission.entity.JudgeSubmissionDetail;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

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
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public JudgeSubmissionDetailResponseDTO(JudgeSubmissionDetail entity) {
        this.id = String.valueOf(entity.getId());
        this.score = entity.getScore();
        this.note = entity.getNote();
        this.createdAt = entity.getCreatedDate();
        this.updatedAt = entity.getLastModifiedDate();

        if (entity.getJudgeSubmission() != null) {
            this.judgeSubmissionId = entity.getJudgeSubmission().getId();
        }

        if (entity.getRoundMarkCriterion() != null) {
            this.roundMarkCriterion = RoundMarkCriterionResponseDTO.builder()
                    .id(String.valueOf(entity.getRoundMarkCriterion().getId()))
                    .name(entity.getRoundMarkCriterion().getName())
                    .maxScore(entity.getRoundMarkCriterion().getMaxScore())
                    .note(entity.getRoundMarkCriterion().getNote())
                    .createdByUserName(
                            entity.getRoundMarkCriterion().getCreatedBy() != null
                                    ? entity.getRoundMarkCriterion()
                                            .getCreatedBy()
                                            .getUsername()
                                    : null)
                    .createdAt(entity.getRoundMarkCriterion().getCreatedDate())
                    .updatedAt(entity.getRoundMarkCriterion().getLastModifiedDate())
                    .judgeSubmissionDetails(null)
                    .build();
        }
    }
}
