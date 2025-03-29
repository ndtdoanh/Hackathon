package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThreadPostReportResponseDTO {
    String id;
    ThreadPostResponseDTO threadPost;
    String reason;
    String status;
    String reviewedById;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
}
