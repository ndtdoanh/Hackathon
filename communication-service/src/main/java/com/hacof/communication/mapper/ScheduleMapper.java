package com.hacof.communication.mapper;

import com.hacof.communication.dto.request.ScheduleRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventResponseDTO;
import com.hacof.communication.dto.response.ScheduleResponseDTO;
import com.hacof.communication.entity.Hackathon;
import com.hacof.communication.entity.Schedule;
import com.hacof.communication.entity.ScheduleEvent;
import com.hacof.communication.entity.Team;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduleMapper {

    public Schedule toEntity(ScheduleRequestDTO requestDTO, Team team, Hackathon hackathon) {
        return Schedule.builder()
                .team(team)
                .hackathon(hackathon)
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .build();
    }

    // Map Schedule entity to ScheduleResponseDTO
    public ScheduleResponseDTO toDto(Schedule schedule) {
        // Convert ScheduleEvents to DTOs
        List<ScheduleEventResponseDTO> scheduleEvents = (schedule.getScheduleEvents() != null)
                ? schedule.getScheduleEvents().stream()
                        .map(this::mapScheduleEventToDto) // Mapping each ScheduleEvent
                        .collect(Collectors.toList())
                : List.of();

        return ScheduleResponseDTO.builder()
                .id(String.valueOf(schedule.getId()))
                .teamId(
                        schedule.getTeam() != null
                                ? String.valueOf(schedule.getTeam().getId())
                                : null)
                .hackathonId(
                        schedule.getHackathon() != null
                                ? String.valueOf(schedule.getHackathon().getId())
                                : null)
                .name(schedule.getName())
                .description(schedule.getDescription())
                .createdDate(schedule.getCreatedDate())
                .lastModifiedDate(schedule.getLastModifiedDate())
                .createdBy(
                        schedule.getCreatedBy() != null
                                ? schedule.getCreatedBy().getUsername()
                                : null)
                .scheduleEvents(scheduleEvents)
                .build();
    }

    private ScheduleEventResponseDTO mapScheduleEventToDto(ScheduleEvent scheduleEvent) {
        return ScheduleEventResponseDTO.builder()
                .id(String.valueOf(scheduleEvent.getId()))
                .name(scheduleEvent.getName())
                .description(scheduleEvent.getDescription())
                .location(scheduleEvent.getLocation())
                .startTime(scheduleEvent.getStartTime())
                .endTime(scheduleEvent.getEndTime())
                .recurrenceRule(scheduleEvent.getRecurrenceRule())
                .createdDate(scheduleEvent.getCreatedDate())
                .lastModifiedDate(scheduleEvent.getLastModifiedDate())
                .createdBy(
                        scheduleEvent.getCreatedBy() != null
                                ? scheduleEvent.getCreatedBy().getUsername()
                                : null)
                .build();
    }
}
