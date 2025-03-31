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

    String id; // ID của ScheduleEventReminder
    ScheduleEventResponseDTO scheduleEvent; // ID của ScheduleEvent mà Reminder này thuộc về
    UserResponse user; // ID của User nhận Reminder
    LocalDateTime remindAt; // Thời gian nhắc nhở
    LocalDateTime createdDate; // Thời gian tạo Reminder
    LocalDateTime lastModifiedDate; // Thời gian sửa đổi Reminder
}
