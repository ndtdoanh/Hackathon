package com.hacof.submission.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

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
