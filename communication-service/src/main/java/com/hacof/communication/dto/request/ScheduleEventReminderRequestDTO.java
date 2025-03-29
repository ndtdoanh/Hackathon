package com.hacof.communication.dto.request;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleEventReminderRequestDTO {

    String scheduleEventId; // ID của ScheduleEvent mà Reminder này thuộc về
    String userId; // ID của User nhận Reminder
    LocalDateTime remindAt; // Thời gian nhắc nhở
}
