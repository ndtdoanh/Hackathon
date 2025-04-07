package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String hackathonId;

    HackathonDTO hackathon;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String mentorId;

    UserDTO mentor;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String teamId;

    TeamDTO team;


    @NotNull(message = "Status is required")
    String status; // enum MentorshipStatus

    LocalDateTime evaluatedAt = LocalDateTime.now();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String evaluatedById;

    UserDTO evaluatedBy;

    String createdByUserName; // save username
    LocalDateTime createdAt;
    String lastModifiedByUserName; // save username
    LocalDateTime updatedAt;
}
