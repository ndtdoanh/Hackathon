package com.hacof.hackathon.dto;

import com.hacof.hackathon.constant.TeamRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRequestReviewDTO {
    private String requestId;
    private TeamRequestStatus status;
    private String note;
}
