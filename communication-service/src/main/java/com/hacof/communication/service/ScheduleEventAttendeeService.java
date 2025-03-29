package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.constant.ScheduleEventStatus;
import com.hacof.communication.dto.request.ScheduleEventAttendeeRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventAttendeeResponseDTO;

public interface ScheduleEventAttendeeService {

    ScheduleEventAttendeeResponseDTO createScheduleEventAttendee(ScheduleEventAttendeeRequestDTO requestDTO);

    ScheduleEventAttendeeResponseDTO updateScheduleEventAttendee(Long id, ScheduleEventAttendeeRequestDTO requestDTO);

    void deleteScheduleEventAttendee(Long id);

    ScheduleEventAttendeeResponseDTO getScheduleEventAttendee(Long id);

    List<ScheduleEventAttendeeResponseDTO> getAllScheduleEventAttendees();

    ScheduleEventAttendeeResponseDTO changeStatus(Long id, ScheduleEventStatus status);
}
