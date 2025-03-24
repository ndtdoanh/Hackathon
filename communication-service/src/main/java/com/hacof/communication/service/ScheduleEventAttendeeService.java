package com.hacof.communication.service;

import com.hacof.communication.constant.ScheduleEventStatus;
import com.hacof.communication.dto.request.ScheduleEventAttendeeRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventAttendeeResponseDTO;

import java.util.List;

public interface ScheduleEventAttendeeService {

    ScheduleEventAttendeeResponseDTO createScheduleEventAttendee(ScheduleEventAttendeeRequestDTO requestDTO);

    ScheduleEventAttendeeResponseDTO updateScheduleEventAttendee(Long id, ScheduleEventAttendeeRequestDTO requestDTO);

}
