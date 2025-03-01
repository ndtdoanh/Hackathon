package com.hacof.submission.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class MentorbookingRequestDTO {
    private Long mentorId;          // Mentor ID
    private Long userId;            // User ID
    private Instant bookingDate;    // Ngày đặt lịch
    private String status;          // Trạng thái booking (PENDING, APPROVED, etc.)
}
