package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

import com.hacof.hackathon.constant.RoundLocationType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoundLocationDTO {
    String id;

    @NotNull(message = "Round ID is required")
    String roundId;

    @NotNull(message = "Location ID is required")
    String locationId;

    @NotNull(message = "Type is required")
    RoundLocationType type;

    String createdByUserName; // save username
    LocalDateTime createdAt;
    String lastModifiedByUserName; // save username
    LocalDateTime updatedAt;
}
