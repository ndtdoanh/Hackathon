package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HackathonResultDTO {
    String id;

    @NotNull(message = "Hackathon Id is required")
    String hackathonId;

    HackathonDTO hackathon;

    @NotNull(message = "Team Id is required")
    String teamId;

    TeamDTO team;

    @NotNull(message = "Total Score is required")
    int totalScore;

    @NotNull(message = "Placement is required")
    int placement;

    @NotNull(message = "Award is required")
    String award;

    String createdByUserName; // save username
    LocalDateTime createdAt;
    String lastModifiedByUserName; // save username
    LocalDateTime updatedAt = LocalDateTime.now();
}
