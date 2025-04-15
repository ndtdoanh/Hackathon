package com.hacof.hackathon.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hacof.hackathon.constant.RoundLocationType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoundLocationDTO {
    String id;

    @JsonIgnore
    String roundId;

    @NotNull
    String locationId;

    LocationDTO location;

    @NotNull(message = "Type is required")
    RoundLocationType type;

    String createdByUserName; // save username

    LocalDateTime createdAt;

    String lastModifiedByUserName; // save username

    LocalDateTime updatedAt = LocalDateTime.now();
}
