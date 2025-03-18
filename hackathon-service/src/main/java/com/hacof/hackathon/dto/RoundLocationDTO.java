package com.hacof.hackathon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoundLocationDTO {
    Long id;

    @NotNull(message = "Round ID is mandatory")
    Long roundId;

    @NotNull(message = "Location ID is mandatory")
    Long locationId;

    @NotBlank(message = "Type is mandatory")
    String type;

    RoundDTO round;
    LocationDTO location;
}
