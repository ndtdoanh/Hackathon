package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.hacof.hackathon.constant.RoundName;
import com.hacof.hackathon.entity.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoundDTO extends AuditBaseDTO {
    private Long id;

    @NotNull(message = "Round name is mandatory (QUALIFYING, SEMIFINAL, FINAL)")
    private RoundName roundName;

    @NotNull(message = "Start date is mandatory")
    private LocalDateTime startDate;

    @NotNull(message = "End date is mandatory")
    private LocalDateTime endDate;

    String description;

    @NotNull(message = "Max team is mandatory")
    private int maxTeam;

    @NotNull(message = "Is video round is mandatory")
    private boolean isVideoRound = false;

    private Long hackathonId;

    List<Submission> submissions;

    List<RoundMarkCriterion> roundMarkCriteria;

    List<JudgeRound> judgeRounds;

    List<TeamRound> teamRounds;

    List<RoundLocation> roundLocations;
}
