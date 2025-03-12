package com.hacof.hackathon.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.hacof.hackathon.entity.ResourceStatus;
import com.hacof.hackathon.entity.ResourceType;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class ResourceDTO {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Resource type is mandatory")
    private ResourceType resourceType;

    @NotNull(message = "Status is mandatory")
    private ResourceStatus status;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @NotNull(message = "Competition round ID is mandatory")
    private Long competitionRoundId;
}
