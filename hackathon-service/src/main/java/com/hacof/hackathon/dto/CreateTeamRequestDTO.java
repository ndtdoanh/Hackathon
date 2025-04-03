package com.hacof.hackathon.dto;

import java.util.List;

import lombok.Data;

@Data
public class CreateTeamRequestDTO {
    private String teamName;
    private List<Long> requestIds;
}
