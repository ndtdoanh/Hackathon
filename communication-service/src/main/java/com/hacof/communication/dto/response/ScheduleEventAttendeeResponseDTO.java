package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import com.hacof.communication.constant.ScheduleEventStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleEventAttendeeResponseDTO {

    String id;
    ScheduleEventResponseDTO scheduleEvent;
    UserResponse user;
    ScheduleEventStatus status;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
}
