package com.hacof.communication.service;

import com.hacof.communication.dto.request.ScheduleEventReminderRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventReminderResponseDTO;

import java.util.List;

public interface ScheduleEventReminderService {

    ScheduleEventReminderResponseDTO createScheduleEventReminder(
            ScheduleEventReminderRequestDTO scheduleEventReminderRequestDTO);

    ScheduleEventReminderResponseDTO updateScheduleEventReminder(
            Long id, ScheduleEventReminderRequestDTO scheduleEventReminderRequestDTO);

    void deleteScheduleEventReminder(Long id);

    ScheduleEventReminderResponseDTO getScheduleEventReminder(Long id);

    List<ScheduleEventReminderResponseDTO> getAllScheduleEventReminders();

    List<ScheduleEventReminderResponseDTO> getScheduleEventRemindersByScheduleEventId(Long scheduleEventId);

    List<ScheduleEventReminderResponseDTO> getScheduleEventRemindersByUserId(Long userId);
}
