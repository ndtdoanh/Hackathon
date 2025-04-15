package com.hacof.hackathon.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTeamRequestDTO {
    private String teamName;
    private List<Long> requestIds;
}
