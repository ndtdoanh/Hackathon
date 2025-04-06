package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IndividualRegistrationRequestDTO {
    String id;

    @NotNull(message = "Hackathon ID is required")
    String hackathonId;

    @NotNull(message = "IndividualRegistrationRequest Status is required")
    String status;

    @NotNull(message = "Reviewer ID is required")
    String reviewedById;

    String createdByUserName; // save username
    LocalDateTime createdAt;
    String lastModifiedByUserName; // save username
    LocalDateTime updatedAt;
}
