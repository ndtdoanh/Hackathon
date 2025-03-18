package com.hacof.hackathon.dto;

import lombok.Data;

@Data
public class EventRegistrationDTO {
    private Long id;
    private Long eventId;
    private Long userId;
    private String status;
}
