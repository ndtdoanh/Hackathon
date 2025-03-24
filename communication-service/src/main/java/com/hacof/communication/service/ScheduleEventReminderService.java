package com.hacof.communication.service;

import com.hacof.communication.dto.request.ScheduleEventReminderRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventReminderResponseDTO;

import java.util.List;

public interface ScheduleEventReminderService {

    ScheduleEventReminderResponseDTO createScheduleEventReminder(ScheduleEventReminderRequestDTO scheduleEventReminderRequestDTO);

}
