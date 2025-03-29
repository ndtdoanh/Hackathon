package com.hacof.communication.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hacof.communication.dto.request.ScheduleEventRequestDTO;
import com.hacof.communication.dto.response.*;
import com.hacof.communication.entity.*;

@Component
public class ScheduleEventMapper {

    // Convert from ScheduleEventRequestDTO to ScheduleEvent entity
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

    // Convert from ScheduleEvent entity to ScheduleEventResponseDTO
    public ScheduleEventResponseDTO toDto(ScheduleEvent scheduleEvent) {
        if (scheduleEvent == null) return null;

        return ScheduleEventResponseDTO.builder()
                .id(String.valueOf(scheduleEvent.getId()))
                .schedule(mapScheduleToResponseDTO(scheduleEvent.getSchedule(), false)) // Tránh lặp vô hạn
                .name(scheduleEvent.getName())
                .description(scheduleEvent.getDescription())
                .location(scheduleEvent.getLocation())
                .startTime(scheduleEvent.getStartTime())
                .endTime(scheduleEvent.getEndTime())
                .isRecurring(scheduleEvent.isRecurring())
                .recurrenceRule(scheduleEvent.getRecurrenceRule())
                .createdDate(scheduleEvent.getCreatedDate())
                .lastModifiedDate(scheduleEvent.getLastModifiedDate())
                .createdBy(
                        scheduleEvent.getCreatedBy() != null
                                ? scheduleEvent.getCreatedBy().getUsername()
                                : null)
                .fileUrls(mapFileUrls(scheduleEvent))
                .build();
    }

    // Map Schedule to ScheduleResponseDTO
    private ScheduleResponseDTO mapScheduleToResponseDTO(Schedule schedule, boolean includeEvents) {
        if (schedule == null) return null;

        return new ScheduleResponseDTO(
                String.valueOf(schedule.getId()),
                schedule.getTeam() != null ? String.valueOf(schedule.getTeam().getId()) : null,
                schedule.getName(),
                schedule.getDescription(),
                schedule.getCreatedDate(),
                schedule.getLastModifiedDate(),
                schedule.getCreatedBy() != null ? schedule.getCreatedBy().getUsername() : null,
                includeEvents && schedule.getScheduleEvents() != null
                        ? schedule.getScheduleEvents().stream().map(this::toDto).collect(Collectors.toList())
                        : List.of() // Tránh ánh xạ vòng lặp vô hạn
                );
    }

    // Map FileUrls
    private List<String> mapFileUrls(ScheduleEvent scheduleEvent) {
        return scheduleEvent.getFileUrls() == null
                ? List.of()
                : scheduleEvent.getFileUrls().stream().map(FileUrl::getFileUrl).collect(Collectors.toList());
    }
}
