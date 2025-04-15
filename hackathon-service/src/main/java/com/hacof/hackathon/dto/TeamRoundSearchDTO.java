package com.hacof.hackathon.dto;

import com.hacof.hackathon.constant.TeamRoundStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
