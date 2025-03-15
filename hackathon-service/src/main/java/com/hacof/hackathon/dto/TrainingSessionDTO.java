package com.hacof.hackathon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainingSessionDTO extends AuditBaseDTO {
    private Long id;
    private String name;
    private String description;
}
