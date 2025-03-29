package com.hacof.submission.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
    String createdBy;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
    List<JudgeSubmissionDetailResponseDTO> judgeSubmissionDetails;
}
