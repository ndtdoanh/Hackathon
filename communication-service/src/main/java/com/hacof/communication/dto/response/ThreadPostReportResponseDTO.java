package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreadPostReportResponseDTO {
    private Long id;
    private ThreadPostResponseDTO threadPost;
    private String reason;
    private String status;
    private Long reviewedById;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
