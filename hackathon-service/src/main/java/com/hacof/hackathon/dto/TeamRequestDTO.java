package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamRequestDTO {
    String id;

    @NotNull(message = "Hackathon ID is required")
    String hackathonId;

    HackathonDTO hackathon;

    @NotNull(message = "Team Name is required")
    String name;

    @JsonIgnore
    String status; // TeamRequestStatus

    String confirmationDeadline; // LocalDateTime

    @NotNull(message = "Note is required")
    String note; // use to store name?

    // String name;
    String reviewedBy; // user reviewed the request

    List<TeamRequestMemberDTO> teamRequestMembers;

    // Audit fields
    String createdByUserName; // save username
    LocalDateTime createdAt;
    String lastModifiedByUserName; // save username
    LocalDateTime updatedAt = LocalDateTime.now();
}
