package com.hacof.communication.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hacof.communication.dto.request.ScheduleRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventResponseDTO;
import com.hacof.communication.dto.response.ScheduleResponseDTO;
import com.hacof.communication.entity.Schedule;
import com.hacof.communication.entity.Team;

@Component
public class ScheduleMapper {

    public Schedule toEntity(ScheduleRequestDTO requestDTO, Team team) {
        return Schedule.builder()
                .team(team)
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .build();
    }

    public ScheduleResponseDTO toDto(Schedule schedule) {
        List<ScheduleEventResponseDTO> scheduleEvents = (schedule.getScheduleEvents() != null)
                ? schedule.getScheduleEvents().stream()
                        .map(scheduleEvent -> ScheduleEventResponseDTO.builder()
                                .id(String.valueOf(scheduleEvent.getId()))
                                .name(scheduleEvent.getName())
                                .description(scheduleEvent.getDescription())
                                .location(scheduleEvent.getLocation())
                                .startTime(scheduleEvent.getStartTime())
                                .endTime(scheduleEvent.getEndTime())
                                .recurrenceRule(scheduleEvent.getRecurrenceRule())
                                .createdDate(scheduleEvent.getCreatedDate())
                                .lastModifiedDate(scheduleEvent.getLastModifiedDate())
                                .createdBy(scheduleEvent.getCreatedBy().getUsername())
                                .build())
                        .collect(Collectors.toList())
                : List.of();

        return ScheduleResponseDTO.builder()
                .id(String.valueOf(schedule.getId()))
                .teamId(
                        schedule.getTeam() != null
                                ? String.valueOf(schedule.getTeam().getId())
                                : null)
                .name(schedule.getName())
                .description(schedule.getDescription())
                .createdDate(schedule.getCreatedDate())
                .lastModifiedDate(schedule.getLastModifiedDate())
                .createdBy(schedule.getCreatedBy().getUsername())
                .scheduleEvents(scheduleEvents)
                .build();
    }
}
