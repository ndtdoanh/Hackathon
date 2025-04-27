package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.request.ScheduleEventReminderRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventReminderResponseDTO;

public interface ScheduleEventReminderService {

    ScheduleEventReminderResponseDTO createScheduleEventReminder(
            ScheduleEventReminderRequestDTO scheduleEventReminderRequestDTO);

    ScheduleEventReminderResponseDTO updateScheduleEventReminder(
            Long id, ScheduleEventReminderRequestDTO scheduleEventReminderRequestDTO);

    void deleteScheduleEventReminder(Long id);

    List<ScheduleEventReminderResponseDTO> getScheduleEventReminder(Long id);

    List<ScheduleEventReminderResponseDTO> getAllScheduleEventReminders();

    List<ScheduleEventReminderResponseDTO> getScheduleEventRemindersByScheduleEventId(Long scheduleEventId);

    List<ScheduleEventReminderResponseDTO> getScheduleEventRemindersByUserId(Long userId);

    List<ScheduleEventReminderResponseDTO> getScheduleEventRemindersByUserIdAndScheduleEventId(Long userId, Long scheduleEventId);
}
