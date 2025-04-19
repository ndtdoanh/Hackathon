package com.hacof.communication.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.ScheduleEventReminderRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventReminderResponseDTO;
import com.hacof.communication.entity.ScheduleEvent;
import com.hacof.communication.entity.ScheduleEventReminder;
import com.hacof.communication.entity.User;
import com.hacof.communication.mapper.ScheduleEventReminderMapper;
import com.hacof.communication.repository.ScheduleEventReminderRepository;
import com.hacof.communication.repository.ScheduleEventRepository;
import com.hacof.communication.repository.UserRepository;
import com.hacof.communication.service.ScheduleEventReminderService;

@Service
public class ScheduleEventReminderServiceImpl implements ScheduleEventReminderService {

    @Autowired
    private ScheduleEventReminderRepository scheduleEventReminderRepository;

    @Autowired
    private ScheduleEventRepository scheduleEventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleEventReminderMapper scheduleEventReminderMapper;

    @Override
    public ScheduleEventReminderResponseDTO createScheduleEventReminder(ScheduleEventReminderRequestDTO requestDTO) {
        if (requestDTO.getScheduleEventId() == null) {
            throw new IllegalArgumentException("scheduleEventId must not be null");
        }

        boolean exists = scheduleEventReminderRepository.existsByScheduleEventIdAndUserId(
                Long.parseLong(requestDTO.getScheduleEventId()), Long.parseLong(requestDTO.getUserId()));

        if (exists) {
            throw new IllegalArgumentException("Reminder already exists for this schedule event and user.");
        }

        ScheduleEvent scheduleEvent = scheduleEventRepository
                .findById(Long.parseLong(requestDTO.getScheduleEventId()))
                .orElseThrow(() -> new IllegalArgumentException("ScheduleEvent not found!"));

        User user = userRepository
                .findById(Long.parseLong(requestDTO.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        if (requestDTO.getRemindAt().isAfter(scheduleEvent.getStartTime())) {
            throw new IllegalArgumentException("Reminder time must be before the start time of the event.");
        }

        ScheduleEventReminder reminder = scheduleEventReminderMapper.toEntity(requestDTO, scheduleEvent, user);
        reminder = scheduleEventReminderRepository.save(reminder);

        return scheduleEventReminderMapper.toDto(reminder);
    }

    @Override
    public ScheduleEventReminderResponseDTO updateScheduleEventReminder(
            Long id, ScheduleEventReminderRequestDTO requestDTO) {
        Optional<ScheduleEventReminder> scheduleEventReminderOptional = scheduleEventReminderRepository.findById(id);
        if (!scheduleEventReminderOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEventReminder not found!");
        }

        if (requestDTO.getScheduleEventId() == null) {
            throw new IllegalArgumentException("scheduleEventId must not be null");
        }

        boolean exists = scheduleEventReminderRepository.existsByScheduleEventIdAndUserId(
                Long.parseLong(requestDTO.getScheduleEventId()), Long.parseLong(requestDTO.getUserId()));

        if (exists) {
            throw new IllegalArgumentException("Reminder already exists for this schedule event and user.");
        }

        ScheduleEvent scheduleEvent = scheduleEventRepository
                .findById(Long.parseLong(requestDTO.getScheduleEventId()))
                .orElseThrow(() -> new IllegalArgumentException("ScheduleEvent not found!"));

        User user = userRepository
                .findById(Long.parseLong(requestDTO.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        if (requestDTO.getRemindAt().isAfter(scheduleEvent.getStartTime())) {
            throw new IllegalArgumentException("Reminder time must be before the start time of the event.");
        }

        ScheduleEventReminder scheduleEventReminder = scheduleEventReminderOptional.get();
        scheduleEventReminder.setScheduleEvent(scheduleEvent);
        scheduleEventReminder.setUser(user);
        scheduleEventReminder.setRemindAt(requestDTO.getRemindAt());

        scheduleEventReminder = scheduleEventReminderRepository.save(scheduleEventReminder);
        return scheduleEventReminderMapper.toDto(scheduleEventReminder);
    }

    @Override
    public void deleteScheduleEventReminder(Long id) {
        Optional<ScheduleEventReminder> scheduleEventReminderOptional = scheduleEventReminderRepository.findById(id);
        if (!scheduleEventReminderOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEventReminder not found!");
        }
        scheduleEventReminderRepository.deleteById(id);
    }

    @Override
    public List<ScheduleEventReminderResponseDTO> getScheduleEventReminder(Long id) {
        Optional<ScheduleEventReminder> scheduleEventReminderOptional = scheduleEventReminderRepository.findById(id);

        // Return empty list if not found
        return scheduleEventReminderOptional
                .map(scheduleEventReminder -> List.of(scheduleEventReminderMapper.toDto(scheduleEventReminder))) // Wrap in a list
                .orElse(List.of()); // Return empty list if not found
    }


    @Override
    public List<ScheduleEventReminderResponseDTO> getAllScheduleEventReminders() {
        List<ScheduleEventReminder> scheduleEventReminders = scheduleEventReminderRepository.findAll();
        return scheduleEventReminders.stream()
                .map(scheduleEventReminderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleEventReminderResponseDTO> getScheduleEventRemindersByScheduleEventId(Long scheduleEventId) {
        List<ScheduleEventReminder> reminders = scheduleEventReminderRepository.findByScheduleEventId(scheduleEventId);
        if (reminders.isEmpty()) {
            return new ArrayList<>(); // Return an empty list if no reminders found for the given scheduleEventId
        }
        return reminders.stream().map(scheduleEventReminderMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleEventReminderResponseDTO> getScheduleEventRemindersByUserId(Long userId) {
        List<ScheduleEventReminder> reminders = scheduleEventReminderRepository.findByUserId(userId);
        if (reminders.isEmpty()) {
            return new ArrayList<>(); // Return an empty list if no reminders found for the given userId
        }
        return reminders.stream().map(scheduleEventReminderMapper::toDto).collect(Collectors.toList());
    }
}
