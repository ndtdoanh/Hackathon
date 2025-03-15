package com.hacof.hackathon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AwardDTO extends AuditBaseDTO {
    private Long id;
    private String name;
    private String description;
    private Double prizeAmount;
    private Long hackathonId;
    private String createdBy;
    private String lastUpdatedBy;
}
