package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CampusDTO {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @NotBlank(message = "createBy is mandatory")
    private String createdBy;

    private LocalDateTime createdDate;

    @NotBlank(message = "lastModifiedBy is mandatory")
    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;

    private List<EventDTO> events = new ArrayList<>();
    private List<MentorDTO> mentors = new ArrayList<>();
    private List<TrainingSessionDTO> trainingSessions = new ArrayList<>();
}
