package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.hacof.hackathon.constant.Name;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompetitionRoundDTO extends AuditBaseDTO {
    private Long id;

    @NotNull(message = "Round name is mandatory (QUALIFYING, SEMIFINAL, FINAL)")
    private Name name;

    private String description;

    @NotNull(message = "Start date is mandatory")
    private LocalDateTime startDate;

    @NotNull(message = "End date is mandatory")
    private LocalDateTime endDate;

    @NotNull(message = "Max team is mandatory")
    private int maxTeam;

    @NotNull(message = "Is video round is mandatory")
    private boolean isVideoRound = false;

    private Long hackathonId;

    private String hackathonName;

    private List<Long> judgeIds;

    private List<Long> teamIds;

    private List<Long> mentorIds;

    private String location;

    private String status;

    private List<String> passedTeams;
}
