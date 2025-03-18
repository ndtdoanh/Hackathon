package com.hacof.hackathon.dto;

import lombok.Data;

@Data
public class MentorshipSessionRequestDTO {
    private Long id;
    private Long mentorshipRequestId;
    private String status;
    private String sessionDetails;
}
