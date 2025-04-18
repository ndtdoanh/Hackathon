package com.hacof.communication.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hacof.communication.dto.request.ScheduleEventRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventResponseDTO;
import com.hacof.communication.dto.response.ScheduleResponseDTO;
import com.hacof.communication.entity.FileUrl;
import com.hacof.communication.entity.Schedule;
import com.hacof.communication.entity.ScheduleEvent;

@Component
public class ScheduleEventMapper {

    // Convert from ScheduleEventRequestDTO to ScheduleEvent entity
    public ScheduleEvent toEntity(ScheduleEventRequestDTO requestDTO, Schedule schedule, List<FileUrl> fileUrls) {
        // Map ScheduleEvent entity from requestDTO
        ScheduleEvent scheduleEvent = ScheduleEvent.builder()
                .schedule(schedule)
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .location(requestDTO.getLocation())
                .startTime(requestDTO.getStartTime())
                .endTime(requestDTO.getEndTime())
                .isRecurring(requestDTO.isRecurring())
                .recurrenceRule(requestDTO.getRecurrenceRule())
                .eventLabel(requestDTO.getEventLabel())
                .build();

        scheduleEvent.setFileUrls(fileUrls);

        return scheduleEvent;
    }

    // Convert from ScheduleEvent entity to ScheduleEventResponseDTO
    public ScheduleEventResponseDTO toDto(ScheduleEvent scheduleEvent) {
        if (scheduleEvent == null) return null;

        // Map file URLs from the scheduleEvent entity (assuming the file URLs are stored as FileUrl objects)
        List<String> fileUrls = scheduleEvent.getFileUrls() != null
                ? scheduleEvent.getFileUrls().stream()
                        .map(FileUrl::getFileUrl) // Assuming FileUrl entity has a method to get the URL string
                        .collect(Collectors.toList())
                : null;

        return ScheduleEventResponseDTO.builder()
                .id(String.valueOf(scheduleEvent.getId()))
                .schedule(mapScheduleToResponseDTO(scheduleEvent.getSchedule(), false)) // Mapping the schedule
                .name(scheduleEvent.getName())
                .description(scheduleEvent.getDescription())
                .location(scheduleEvent.getLocation())
                .startTime(scheduleEvent.getStartTime())
                .endTime(scheduleEvent.getEndTime())
                .isRecurring(scheduleEvent.isRecurring())
                .recurrenceRule(scheduleEvent.getRecurrenceRule())
                .eventLabel(scheduleEvent.getEventLabel())
                .createdDate(scheduleEvent.getCreatedDate())
                .lastModifiedDate(scheduleEvent.getLastModifiedDate())
                .createdBy(
                        scheduleEvent.getCreatedBy() != null
                                ? scheduleEvent.getCreatedBy().getUsername()
                                : null)
                .fileUrls(fileUrls) // Adding the file URLs
                .build();
    }

    // Map Schedule to ScheduleResponseDTO
    private ScheduleResponseDTO mapScheduleToResponseDTO(Schedule schedule, boolean includeEvents) {
        if (schedule == null) return null;

        return new ScheduleResponseDTO(
                String.valueOf(schedule.getId()),
                schedule.getTeam() != null ? String.valueOf(schedule.getTeam().getId()) : null,
                schedule.getHackathon() != null
                        ? String.valueOf(schedule.getHackathon().getId())
                        : null,
                schedule.getName(),
                schedule.getDescription(),
                schedule.getCreatedDate(),
                schedule.getLastModifiedDate(),
                schedule.getCreatedBy() != null ? schedule.getCreatedBy().getUsername() : null,
                includeEvents && schedule.getScheduleEvents() != null
                        ? schedule.getScheduleEvents().stream().map(this::toDto).collect(Collectors.toList())
                        : List.of());
    }
}
