package com.hacof.communication.dto.request;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEventReminderRequestDTO {

    private Long scheduleEventId; // ID của ScheduleEvent mà Reminder này thuộc về
    private Long userId; // ID của User nhận Reminder
    private LocalDateTime remindAt; // Thời gian nhắc nhở
}
