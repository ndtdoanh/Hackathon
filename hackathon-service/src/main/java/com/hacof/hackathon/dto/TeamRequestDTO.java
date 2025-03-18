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
public class TeamRequestDTO {
    private long id;
    private String teamName;
    private String description;
    private long hackathonId;
    private long leaderId;
    private Status status; // PENDING, APPROVED, REJECTED, PROCESSING
    private long reviewedBy;
    private LocalDateTime reviewedAt;
    private String createdBy;
    private LocalDateTime createdDate;
}
