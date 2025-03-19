package com.hacof.hackathon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalRequestDTO {
    private Long requestId;
    private Long reviewerId;
    private Long leaderId;
}
