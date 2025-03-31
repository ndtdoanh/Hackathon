package com.hacof.submission.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class JudgeRoundResponseDTO {
    String id;
    UserResponse judge;
    RoundResponseDTO round;
    boolean isDeleted;
    String createdByUserName;
    String createdAt;
    String updatedAt;
}
