package com.hacof.hackathon.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamBulkRequestDTO {

    @NotNull(message = "Team Leader ID cannot be null")
    String teamLeaderId;

    @NotEmpty(message = "Team Members cannot be empty")
    List<TeamMemberBulkDTO> teamMembers;

    @NotEmpty(message = "Team Hackathons cannot be empty")
    List<TeamHackathonBulkDTO> teamHackathons;
}
