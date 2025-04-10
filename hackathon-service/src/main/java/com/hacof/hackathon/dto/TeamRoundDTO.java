package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import com.hacof.hackathon.constant.TeamRoundStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
