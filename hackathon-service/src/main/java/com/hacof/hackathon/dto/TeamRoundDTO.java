package com.hacof.hackathon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamRoundDTO {
    private String id;
    private String teamId;
    private String roundId;
    private int score;
}
