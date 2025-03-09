package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EventDTO {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;

    @NotBlank(message = "Created by is mandatory")
    private String createdBy;

    @NotNull(message = "Created date is mandatory")
    private LocalDateTime createdDate;

    @NotBlank(message = "Last updated by is mandatory")
    private String lastUpdatedBy;

    @NotNull(message = "Last updated date is mandatory")
    private LocalDateTime lastUpdatedDate;
}
