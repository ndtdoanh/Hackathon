package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleEventReminderResponseDTO {

    String id;
    ScheduleEventResponseDTO scheduleEvent;
    UserResponse user;
    LocalDateTime remindAt;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
}
