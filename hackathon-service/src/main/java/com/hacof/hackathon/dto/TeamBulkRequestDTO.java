package com.hacof.hackathon.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamBulkRequestDTO {

    @NotNull(message = "Team Leader ID cannot be null")
    private String teamLeaderId;

    @NotEmpty(message = "Team Members cannot be empty")
    private List<TeamMemberBulkDTO> teamMembers;

    @NotEmpty(message = "Team Hackathons cannot be empty")
    private List<TeamHackathonBulkDTO> teamHackathons;
}

