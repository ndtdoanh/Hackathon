package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.request.ScheduleEventRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventResponseDTO;

public interface ScheduleEventService {

    ScheduleEventResponseDTO createScheduleEvent(ScheduleEventRequestDTO scheduleEventRequestDTO);

    ScheduleEventResponseDTO updateScheduleEvent(Long id, ScheduleEventRequestDTO scheduleEventRequestDTO);

    void deleteScheduleEvent(Long id);

    ScheduleEventResponseDTO getScheduleEvent(Long id);

    List<ScheduleEventResponseDTO> getAllScheduleEvents();

    List<ScheduleEventResponseDTO> getScheduleEventsByScheduleId(Long scheduleId);
}
