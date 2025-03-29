package com.hacof.submission.dto.response;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgeRoundResponseDTO {
    private Long id;
    private UserResponse judge;
    private RoundResponseDTO round;
    private boolean isDeleted;
    private String createdBy;
    private String createdDate;
    private String lastModifiedDate;
}
