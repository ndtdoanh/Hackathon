package com.hacof.submission.dtos.request;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentorbookingRequestDTO {
    private Long mentorId; // Mentor ID
    private Long userId; // User ID
    private Instant bookingDate; // Ngày đặt lịch
}
