package com.hacof.hackathon.dto;

import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamHackathonBulkDTO {
    @NotNull(message = "Hackathon ID cannot be null")
    String hackathonId;

    @NotNull(message = "Status cannot be null")
    String status; // "ACTIVE" or "INACTIVE"
}
