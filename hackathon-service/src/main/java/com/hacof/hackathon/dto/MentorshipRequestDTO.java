package com.hacof.hackathon.dto;

import lombok.Data;

@Data
public class MentorshipRequestDTO {
    private Long id;
    private Long teamId;
    private Long mentorId;
    private String status;
}
