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
public class MentorshipRequestDTO {
    String id;

    @NotNull(message = "Hackathon ID is required")
    String hackathonId;

    @NotNull(message = "Mentor ID is required")
    String mentorId;

    @NotNull(message = "Team ID is required")
    String teamId;

    @NotNull(message = "Status is required")
    String status;

    LocalDateTime evaluatedAt = LocalDateTime.now();

    @NotNull(message = "Evaluated By ID is required")
    String evaluatedById;

    String createdByUserName; // save username
    LocalDateTime createdAt;
    String lastModifiedByUserName; // save username
    LocalDateTime updatedAt;
}
