package com.hacof.hackathon.dto;

import com.hacof.hackathon.constant.TeamRoundStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamRoundDTO {
    String id;

    String teamId;
    TeamDTO team;

    String roundId;
    RoundDTO round;

    TeamRoundStatus status;

    String description;

    String createdByUserName; // save username
    LocalDateTime createdAt;
    String lastModifiedByUserName; // save username
    LocalDateTime updatedAt = LocalDateTime.now();
}
