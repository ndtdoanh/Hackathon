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
public class MentorshipSessionRequestDTO {
    String id;

    // @NotNull(message = "Mentor Team ID is required")
    String mentorTeamId;
    MentorTeamDTO mentorTeam;

    @NotNull(message = "Start Time is required")
    LocalDateTime startTime;

    @NotNull(message = "End Time is required")
    LocalDateTime endTime;

    @NotNull(message = "Location is required")
    String location;

    @NotNull(message = "Description is required")
    String description;

    @NotNull(message = "Status is required")
    String status;

    // @NotNull(message = "Evaluated By ID is required")
    String evaluatedById;

    UserDTO evaluatedBy;

    LocalDateTime evaluatedAt;

    String createdByUserName;
    LocalDateTime createdAt;
    String lastModifiedByUserName;
    LocalDateTime updatedAt = LocalDateTime.now();
}
