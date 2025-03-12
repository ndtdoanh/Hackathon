package com.hacof.hackathon.dto;

import lombok.Data;

@Data
public class AwardDTO {
    private Long id;
    private String name;
    private String description;
    private int prizeAmount;
    private Long hackathonId;
    private String createdBy;
    private String lastUpdatedBy;
}
