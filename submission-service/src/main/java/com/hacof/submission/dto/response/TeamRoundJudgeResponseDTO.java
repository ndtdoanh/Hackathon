package com.hacof.submission.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamRoundJudgeResponseDTO {

    String id;
    TeamRoundResponseDTO teamRound;
    UserResponse judge;
    String createdByUserName;
    String createdAt;
    String updatedAt;
}
