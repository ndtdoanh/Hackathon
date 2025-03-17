package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import com.hacof.hackathon.constant.EventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime eventDate;
    private boolean notificationSent;
    private EventType eventType;
    private Long hackathonId;
    private Long organizerId;

    // Additional fields for response
    private String organizerName;
    private String hackathonName;
}
