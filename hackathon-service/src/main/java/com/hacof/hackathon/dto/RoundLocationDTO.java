package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hacof.hackathon.constant.RoundLocationType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoundLocationDTO {
    String id;

    @JsonIgnore
    String roundId;

    String locationId;

    @NotNull(message = "Type is required")
    RoundLocationType type;

    @JsonIgnore
    String createdByUserName; // save username

    @JsonIgnore
    LocalDateTime createdAt;

    @JsonIgnore
    String lastModifiedByUserName; // save username

    @JsonIgnore
    LocalDateTime updatedAt;
}
