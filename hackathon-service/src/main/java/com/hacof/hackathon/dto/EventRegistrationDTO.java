package com.hacof.hackathon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventRegistrationDTO {
    private Long id;
    private Long eventId;
    private Long userId;
    private String status;
}
