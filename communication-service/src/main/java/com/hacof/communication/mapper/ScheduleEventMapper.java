package com.hacof.communication.mapper;

import com.hacof.communication.dto.request.ScheduleEventRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventResponseDTO;
import com.hacof.communication.entity.ScheduleEvent;
import com.hacof.communication.entity.Schedule;
import org.springframework.stereotype.Component;

@Component
public class ScheduleEventMapper {

    // Chuyển từ ScheduleEventRequestDTO sang ScheduleEvent entity
    public ScheduleEvent toEntity(ScheduleEventRequestDTO requestDTO, Schedule schedule) {
        return ScheduleEvent.builder()
                .schedule(schedule)
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .location(requestDTO.getLocation())
                .startTime(requestDTO.getStartTime())
                .endTime(requestDTO.getEndTime())
                .isRecurring(requestDTO.isRecurring())
                .recurrenceRule(requestDTO.getRecurrenceRule())
                .build();
    }

    // Chuyển từ ScheduleEvent entity sang ScheduleEventResponseDTO
    public ScheduleEventResponseDTO toDto(ScheduleEvent scheduleEvent) {
        return ScheduleEventResponseDTO.builder()
                .id(scheduleEvent.getId())
                .scheduleId(scheduleEvent.getSchedule().getId())
                .name(scheduleEvent.getName())
                .description(scheduleEvent.getDescription())
                .location(scheduleEvent.getLocation())
                .startTime(scheduleEvent.getStartTime())
                .endTime(scheduleEvent.getEndTime())
                .isRecurring(scheduleEvent.isRecurring())
                .recurrenceRule(scheduleEvent.getRecurrenceRule())
                .createdDate(scheduleEvent.getCreatedDate())
                .lastModifiedDate(scheduleEvent.getLastModifiedDate())
                .createdBy(scheduleEvent.getCreatedBy().getUsername()) // Giả sử User có trường `username`
                .build();
    }
}
