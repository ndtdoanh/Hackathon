package com.hacof.submission.dto.response;

import com.hacof.submission.constant.TeamRoundStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamRoundResponseDTO {
    String id;
    String teamId;
    String roundId;
    TeamRoundStatus status;
    String description;
    String createdByUserName;
    String createdAt;
    String updatedAt;
}
