package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import com.hacof.hackathon.constant.EventType;

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
public class EventDTO {
    private long id;
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
