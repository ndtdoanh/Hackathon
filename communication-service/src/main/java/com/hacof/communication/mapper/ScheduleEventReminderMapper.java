package com.hacof.communication.mapper;

import com.hacof.communication.dto.request.ScheduleEventReminderRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventReminderResponseDTO;
import com.hacof.communication.entity.ScheduleEventReminder;
import com.hacof.communication.entity.ScheduleEvent;
import com.hacof.communication.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ScheduleEventReminderMapper {

    // Chuyển từ ScheduleEventReminderRequestDTO sang ScheduleEventReminder entity
    public ScheduleEventReminder toEntity(ScheduleEventReminderRequestDTO requestDTO, ScheduleEvent scheduleEvent, User user) {
        return ScheduleEventReminder.builder()
                .scheduleEvent(scheduleEvent)
                .user(user)
                .remindAt(requestDTO.getRemindAt())
                .build();
    }

    // Chuyển từ ScheduleEventReminder entity sang ScheduleEventReminderResponseDTO
    public ScheduleEventReminderResponseDTO toDto(ScheduleEventReminder scheduleEventReminder) {
        return ScheduleEventReminderResponseDTO.builder()
                .id(scheduleEventReminder.getId())
                .scheduleEventId(scheduleEventReminder.getScheduleEvent().getId())
                .userId(scheduleEventReminder.getUser().getId())
                .remindAt(scheduleEventReminder.getRemindAt())
                .createdDate(scheduleEventReminder.getCreatedDate())
                .lastModifiedDate(scheduleEventReminder.getLastModifiedDate())
                .build();
    }
}
