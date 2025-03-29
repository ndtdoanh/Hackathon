package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import com.hacof.communication.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEventReminderResponseDTO {

    private Long id;              // ID của ScheduleEventReminder
    private ScheduleEventResponseDTO scheduleEvent; // ID của ScheduleEvent mà Reminder này thuộc về
    private UserResponse user;          // ID của User nhận Reminder
    private LocalDateTime remindAt; // Thời gian nhắc nhở
    private LocalDateTime createdDate;  // Thời gian tạo Reminder
    private LocalDateTime lastModifiedDate; // Thời gian sửa đổi Reminder
}
