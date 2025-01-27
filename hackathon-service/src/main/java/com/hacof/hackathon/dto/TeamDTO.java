package com.hacof.hackathon.dto;

import java.util.List;

import lombok.Data;

@Data
public class TeamDTO {
    private Long id;
    private String name;
    private Long hackathonId;
    private Long leaderId;
    private List<Long> memberIds;
    private String createdBy;
    private String lastUpdatedBy;
}
