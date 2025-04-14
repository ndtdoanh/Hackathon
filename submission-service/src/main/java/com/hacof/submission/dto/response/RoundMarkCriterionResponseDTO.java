package com.hacof.submission.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoundMarkCriterionResponseDTO {
    String id;
    RoundResponseDTO round;
    String name;
    int maxScore;
    String note;
    String createdByUserName;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<JudgeSubmissionDetailResponseDTO> judgeSubmissionDetails;
}
