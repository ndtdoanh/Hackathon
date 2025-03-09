package com.hacof.submission.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class MentorbookingResponseDTO {
    private Long id;                // ID của Mentorbooking
    private Long mentorId;          // Mentor ID
    private Long userId;            // User ID
    private Instant bookingDate;    // Ngày đặt lịch
    private String status;          // Trạng thái booking
    private Instant createdAt;      // Ngày tạo
    private Instant updatedAt;      // Ngày cập nhật
}
