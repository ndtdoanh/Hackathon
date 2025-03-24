package com.hacof.communication.service;

import com.hacof.communication.dto.request.ScheduleRequestDTO;
import com.hacof.communication.dto.response.ScheduleResponseDTO;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDTO createSchedule(ScheduleRequestDTO scheduleRequestDTO);

}
