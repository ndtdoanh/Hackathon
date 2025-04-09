package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.request.ScheduleRequestDTO;
import com.hacof.communication.dto.response.ScheduleResponseDTO;

public interface ScheduleService {

    ScheduleResponseDTO createSchedule(ScheduleRequestDTO scheduleRequestDTO);

    ScheduleResponseDTO updateSchedule(Long id, ScheduleRequestDTO scheduleRequestDTO);

    void deleteSchedule(Long id);

    ScheduleResponseDTO getSchedule(Long id);

    List<ScheduleResponseDTO> getAllSchedules();

    List<ScheduleResponseDTO> getSchedulesByTeamId(Long teamId);

    List<ScheduleResponseDTO> getSchedulesByCreatedByUsernameAndHackathonId(String createdByUsername, Long hackathonId);

    List<ScheduleResponseDTO> getSchedulesByTeamIdAndHackathonId(Long teamId, Long hackathonId);
}
