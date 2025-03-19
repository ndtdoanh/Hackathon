package com.hacof.communication.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreadPostReportRequestDTO {
    private Long threadPostId;
    private String reason;
}
