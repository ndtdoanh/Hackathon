package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    HackathonDTO hackathon;

    @NotNull(message = "Mentor Id is required")
    String mentorId;
    UserDTO mentor;

    @NotNull(message = "Team Id is required")
    String teamId;
    TeamDTO team;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<MentorshipSessionRequestDTO> mentorshipSessionRequests;

    String createdByUserName;
    LocalDateTime createdAt;
    String lastModifiedByUserName;
    LocalDateTime updatedAt = LocalDateTime.now();
}
