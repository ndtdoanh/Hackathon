package com.hacof.hackathon.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDTO extends AuditBaseDTO {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;
}
