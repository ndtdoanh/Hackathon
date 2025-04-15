package com.hacof.submission.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoundResponseDTO {
    String id;
    HackathonResponseDTO hackathon;
    LocalDateTime startTime;
    LocalDateTime endTime;
    int roundNumber;
    String roundTitle;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
