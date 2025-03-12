package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class CompetitionRoundDTO {
    private Long id;

    @NotNull(message = "Round name is mandatory (QUALIFYING, SEMIFINAL, FINAL)")
    private RoundType name;

    private String description;

    @NotNull(message = "Start date is mandatory")
    private LocalDateTime startDate;

    @NotNull(message = "End date is mandatory")
    private LocalDateTime endDate;

    @NotNull(message = "Max team is mandatory")
    private int maxTeam;

    @NotNull(message = "Is video round is mandatory")
    private boolean isVideoRound = false;

    @NotBlank(message = "Created by is mandatory")
    private String createdBy;

    @NotNull(message = "Created date is mandatory")
    private LocalDateTime createdDate;

    @NotBlank(message = "Last modified by is mandatory")
    private String lastModifiedBy;

    @NotNull(message = "Last modified date is mandatory")
    private LocalDateTime lastModifiedDate;

    private Long hackathonId;

    private String hackathonName;

    private List<Long> judgeIds;

    private List<Long> teamIds;

    private List<Long> mentorIds;

    private String location;

    private String status;

    private List<String> passedTeams;
}
