package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoundLocationResponseDTO {
    Long roundLocationId;
    Long roundId;
    String roundTitle;
    int roundNumber;
    String roundStatus;
    LocalDateTime roundStartTime;
    LocalDateTime roundEndTime;
    Long locationId;
    String locationName;
    String locationAddress;
    Double locationLatitude;
    Double locationLongitude;
    String roundLocationType;
}
