package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import com.hacof.hackathon.constant.RoundType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompetitionRoundDTO extends AuditBaseDTO {
    Long id;

    @NotNull(message = "Round name is mandatory (QUALIFYING, SEMIFINAL, FINAL)")
    RoundType name;

    String description;

    @NotNull(message = "Start date is mandatory")
    LocalDateTime startDate;

    @NotNull(message = "End date is mandatory")
    LocalDateTime endDate;

    @NotNull(message = "Max team is mandatory")
    @Min(value = 1, message = "Max team must be at least 1")
    int maxTeam;

    boolean isVideoRound = false;

    @NotNull(message = "Hackathon ID is mandatory")
    Long hackathonId;

    List<Long> judgeIds;

    List<Long> mentorIds;

    //    private List<Long> teamIds;
    //
    //    private String location;
    //
    //    private String status;
    //
    //    private List<String> passedTeams;
}
