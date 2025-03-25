package com.hacof.communication.mapper;

import org.springframework.stereotype.Component;

import com.hacof.communication.constant.ScheduleEventStatus;
import com.hacof.communication.dto.request.ScheduleEventAttendeeRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventAttendeeResponseDTO;
import com.hacof.communication.entity.ScheduleEvent;
import com.hacof.communication.entity.ScheduleEventAttendee;
import com.hacof.communication.entity.User;

@Component
public class ScheduleEventAttendeeMapper {

    // Chuyển từ ScheduleEventAttendeeRequestDTO sang ScheduleEventAttendee entity
    public ScheduleEventAttendee toEntity(
            ScheduleEventAttendeeRequestDTO requestDTO, ScheduleEvent scheduleEvent, User user) {
        return ScheduleEventAttendee.builder()
                .scheduleEvent(scheduleEvent)
                .user(user)
                .statusD(ScheduleEventStatus.INVITED) // Mặc định là INVITED
                .build();
    }

    // Chuyển từ ScheduleEventAttendee entity sang ScheduleEventAttendeeResponseDTO
    public ScheduleEventAttendeeResponseDTO toDto(ScheduleEventAttendee scheduleEventAttendee) {
        return ScheduleEventAttendeeResponseDTO.builder()
                .id(scheduleEventAttendee.getId())
                .scheduleEventId(scheduleEventAttendee.getScheduleEvent().getId())
                .userId(scheduleEventAttendee.getUser().getId())
                .status(scheduleEventAttendee.getStatusD())
                .createdDate(scheduleEventAttendee.getCreatedDate())
                .lastModifiedDate(scheduleEventAttendee.getLastModifiedDate())
                .build();
    }
}
