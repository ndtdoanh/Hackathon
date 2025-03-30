package com.hacof.hackathon.dto;

import com.hacof.hackathon.constant.TeamRequestStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRequestReviewDTO {
    private String requestId;
    private TeamRequestStatus status;
    private String note;
}
