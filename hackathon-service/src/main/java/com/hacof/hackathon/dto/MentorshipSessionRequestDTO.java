package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MentorshipSessionRequestDTO {
    private String id;
    private String mentorTeamId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private String description;
    private String status;
    private String evaluatedById;
    private LocalDateTime evaluatedAt;
}
