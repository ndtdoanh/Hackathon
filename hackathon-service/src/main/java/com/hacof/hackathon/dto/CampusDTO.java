package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class CampusDTO {
    private Long id;

    @NotNull(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Invalid location for the campus")
    private String location;

    @NotNull(message = "Created by is mandatory")
    private String createdBy;

    private LocalDateTime createdDate;

    @NotNull(message = "Last modified by is mandatory")
    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;

    @JsonIgnore
    private List<EventDTO> events = new ArrayList<>();

    @JsonIgnore
    private List<MentorDTO> mentors = new ArrayList<>();

    @JsonIgnore
    private List<TrainingSessionDTO> trainingSessions = new ArrayList<>();
}
