package com.hacof.submission.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import java.time.LocalDateTime;
import java.util.List;

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
