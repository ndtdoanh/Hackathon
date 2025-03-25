package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEventReminderResponseDTO {

    private Long id; // ID của ScheduleEventReminder
    private Long scheduleEventId; // ID của ScheduleEvent mà Reminder này thuộc về
    private Long userId; // ID của User nhận Reminder
    private LocalDateTime remindAt; // Thời gian nhắc nhở
    private LocalDateTime createdDate; // Thời gian tạo Reminder
    private LocalDateTime lastModifiedDate; // Thời gian sửa đổi Reminder
}
