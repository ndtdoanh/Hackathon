package com.hacof.hackathon.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonPropertyOrder({
    "id",
    "name",
    "location",
    "events",
    "mentors",
    "trainingSessions",
    "createdBy",
    "createdDate",
    "lastModifiedBy",
    "lastModifiedDate"
})
public class CampusDTO extends AuditBaseDTO {
    Long id;

    @NotNull(message = "Name is mandatory")
    String name;

    @NotBlank(message = "Invalid location for the campus")
    String location;

    @JsonIgnore
    List<EventDTO> events = new ArrayList<>();

    @JsonIgnore
    List<MentorDTO> mentors = new ArrayList<>();

    @JsonIgnore
    List<TrainingSessionDTO> trainingSessions = new ArrayList<>();
}
