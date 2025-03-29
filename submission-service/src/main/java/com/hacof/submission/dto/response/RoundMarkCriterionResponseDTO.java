package com.hacof.submission.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder  // Ensure this annotation is present
@NoArgsConstructor
@AllArgsConstructor
public class RoundMarkCriterionResponseDTO {
    private Long id;
    private RoundResponseDTO round;  // Ensure this field exists
    private String name;
    private int maxScore;
    private String note;
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private List<JudgeSubmissionDetailResponseDTO> judgeSubmissionDetails;
}
