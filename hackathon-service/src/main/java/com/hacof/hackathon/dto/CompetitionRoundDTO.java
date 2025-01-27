package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import com.hacof.hackathon.constant.RoundType;

import lombok.Data;

@Data
public class CompetitionRoundDTO {
    private Long id;
    private RoundType name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long hackathonId;
    private int maxTeam;
    private boolean isVideoRound;
}
