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
