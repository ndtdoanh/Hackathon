package com.hacof.hackathon.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamHackathonBulkDTO {
    @NotNull(message = "Hackathon ID cannot be null")
    private String hackathonId;

    @NotNull(message = "Status cannot be null")
    private String status; // "ACTIVE" or "INACTIVE"
}
