package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hacof.hackathon.util.CustomLocalDateTimeDeserialized;

import lombok.Data;

@Data
public class HackathonDTO {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String bannerImageUrl;
    private String description;

    @NotNull(message = "Start date is mandatory")
    @JsonDeserialize(using = CustomLocalDateTimeDeserialized.class)
    private LocalDateTime startDate;

    @NotNull(message = "Start date is mandatory")
    @JsonDeserialize(using = CustomLocalDateTimeDeserialized.class)
    private LocalDateTime endDate;

    private Long organizerId;
    private String organizerName;
    private List<CompetitionRoundDTO> rounds;
    private List<EventDTO> events;
    private List<JudgeDTO> judges;
    // private List<CheckInDTO> checkIns;
    private List<ResourceDTO> resources;

    @NotBlank(message = "Created by is mandatory")
    private String createdBy;

    @NotNull(message = "Created date is mandatory")
    private LocalDateTime createdDate;

    @NotBlank(message = "Last modified by is mandatory")
    private String lastModifiedBy;

    @NotNull(message = "Last modified date is mandatory")
    private LocalDateTime lastModifiedDate;
}
