package com.hacof.hackathon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoundMarkCriterionDTO {
    private String id;
    private String criterion;
    private int maxScore;
}
