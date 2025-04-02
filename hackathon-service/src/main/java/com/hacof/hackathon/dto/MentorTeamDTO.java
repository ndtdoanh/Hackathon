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
public class MentorTeamDTO {
    String id;

    @NotNull(message = "Hackathon Id is required")
    String hackathonId;

    @NotNull(message = "Mentor Id is required")
    String mentorId;

    @NotNull(message = "Team Id is required")
    String teamId;

    String createdByUserName;
    LocalDateTime createdAt;
    String lastModifiedByUserName;
    LocalDateTime updatedAt = LocalDateTime.now();
}
