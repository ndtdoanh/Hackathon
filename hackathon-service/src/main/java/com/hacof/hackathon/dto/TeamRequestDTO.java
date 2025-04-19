package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamRequestDTO {
    String id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull(message = "Hackathon ID is required")
    String hackathonId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    HackathonDTO hackathon;

    @NotNull(message = "Team Name is required")
    String name;

    String status; // TeamRequestStatus

    String confirmationDeadline; // LocalDateTime

    String note; // optional field

    // String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String reviewById;

    UserDTO reviewedBy; // user reviewed the request

    List<TeamRequestMemberDTO> teamRequestMembers;

    // Audit fields
    String createdByUserName; // save username
    LocalDateTime createdAt;
    String lastModifiedByUserName; // save username
    LocalDateTime updatedAt = LocalDateTime.now();
}
