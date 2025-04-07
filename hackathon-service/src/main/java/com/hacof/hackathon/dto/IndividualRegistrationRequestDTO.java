package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String hackathonId;
    HackathonDTO hackathon;

    @NotNull(message = "IndividualRegistrationRequest Status is required")
    String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String reviewById;
    UserDTO reviewedBy;

    String createdByUserName; // save username
    LocalDateTime createdAt;
    String lastModifiedByUserName; // save username
    LocalDateTime updatedAt;
}
