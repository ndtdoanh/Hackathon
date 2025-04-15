package com.hacof.communication.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThreadPostReportResponseDTO {
    String id;
    ThreadPostResponseDTO threadPost;
    String reason;
    String status;
    String reviewedById;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
