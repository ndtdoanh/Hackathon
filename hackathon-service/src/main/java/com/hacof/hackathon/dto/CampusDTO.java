package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CampusDTO {
    private Long id;
    private String name;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private String createdBy;
    private String lastUpdatedBy;
}
