package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import com.hacof.hackathon.constant.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTeamRequestDTO {
    private Long id;
    private Long teamId;
    private Long userId;
    private String requestType; // INVITATION, JOIN_REQUEST
    private Status status; // PENDING, APPROVED, REJECTED
    private String createdBy;
    private LocalDateTime createdDate;
}
