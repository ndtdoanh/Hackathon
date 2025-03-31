package com.hacof.hackathon.dto;

import com.hacof.hackathon.constant.TeamRoundStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRoundSearchDTO {
    private String teamId;
    private String roundId;
    private TeamRoundStatus status;
    private int page;
    private int size;
    private String sortBy;
    private String sortDirection;
}
