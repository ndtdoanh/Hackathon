package com.hacof.hackathon.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateTeamRequestDTO {
    private String teamName;
    private List<Long> requestIds;
}
