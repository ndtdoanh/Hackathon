package com.hacof.hackathon.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MentorTeamLimitDTO {
    String id;

    @NotNull(message = "Hackathon ID is required")
    String hackathonId;

    HackathonDTO hackathon;

    @NotNull(message = "Mentor ID is required")
    String mentorId;

    UserDTO mentor;

    @NotNull(message = "Team ID is required")
    String teamId;

    TeamDTO team;

    @NotNull(message = "Max Teams is required")
    int maxTeams;

    @NotNull(message = "Max Mentors is required")
    int maxMentors;

    String createdByUserName; // save username
    LocalDateTime createdAt;
    String lastModifiedByUserName; // save username
    LocalDateTime updatedAt;
}
