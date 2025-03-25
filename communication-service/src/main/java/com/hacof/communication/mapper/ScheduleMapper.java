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

    // Chuyển từ ScheduleRequestDTO sang Schedule entity
    public Schedule toEntity(ScheduleRequestDTO requestDTO, Team team) {
        return Schedule.builder()
                .team(team)
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .build();
    }

    // Chuyển từ Schedule entity sang ScheduleResponseDTO
    public ScheduleResponseDTO toDto(Schedule schedule) {
        // Kiểm tra nếu scheduleEvents không phải là null và dùng stream, nếu null trả về danh sách trống
        List<ScheduleEventResponseDTO> scheduleEvents = (schedule.getScheduleEvents() != null)
                ? schedule.getScheduleEvents().stream()
                        .map(scheduleEvent -> ScheduleEventResponseDTO.builder()
                                .id(scheduleEvent.getId())
                                .name(scheduleEvent.getName())
                                .startTime(scheduleEvent.getStartTime())
                                .endTime(scheduleEvent.getEndTime())
                                .build()) // Dùng builder pattern
                        .collect(Collectors.toList())
                : List.of(); // Trả về danh sách trống nếu scheduleEvents là null

        return ScheduleResponseDTO.builder()
                .id(schedule.getId())
                .teamId(schedule.getTeam().getId())
                .name(schedule.getName())
                .description(schedule.getDescription())
                .createdDate(schedule.getCreatedDate())
                .lastModifiedDate(schedule.getLastModifiedDate())
                .createdBy(schedule.getCreatedBy().getUsername()) // Giả sử User có trường `username`
                .scheduleEvents(scheduleEvents)
                .build();
    }
}
