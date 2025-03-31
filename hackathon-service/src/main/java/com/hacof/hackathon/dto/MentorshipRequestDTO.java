package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import com.hacof.hackathon.constant.MentorshipStatus;

import lombok.Data;

@Data
public class MentorshipRequestDTO {
    private String id;
    private String hackathonId;
    private String mentorId;
    private String teamId;
    private MentorshipStatus status;
    private LocalDateTime evaluatedAt;
    private String evaluatedById;
}
