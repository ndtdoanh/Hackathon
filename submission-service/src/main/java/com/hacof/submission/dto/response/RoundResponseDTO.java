package com.hacof.submission.dto.response;

import java.time.LocalDateTime;

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
public class RoundResponseDTO {
    private Long id;
    private HackathonResponseDTO hackathon;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int roundNumber;
    private String roundTitle;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
