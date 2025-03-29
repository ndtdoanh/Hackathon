package com.hacof.communication.service.impl;

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
        // Tìm ScheduleEvent theo scheduleEventId
        Long scheduleEventId = Long.parseLong(requestDTO.getScheduleEventId());
        Optional<ScheduleEvent> scheduleEventOptional = scheduleEventRepository.findById(scheduleEventId);
        if (!scheduleEventOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEvent not found!");
        }

        // Kiểm tra xem remindAt có trước startTime của ScheduleEvent không
        ScheduleEvent scheduleEvent = scheduleEventOptional.get();
        if (requestDTO.getRemindAt().isAfter(scheduleEvent.getStartTime())) {
            throw new IllegalArgumentException("Reminder time (remindAt) must be before the start time of the event.");
        }

        // Tìm User theo userId
        Long userId = Long.parseLong(requestDTO.getUserId());
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found!");
        }

        // Chuyển từ ScheduleEventReminderRequestDTO thành ScheduleEventReminder entity
        ScheduleEventReminder scheduleEventReminder =
                scheduleEventReminderMapper.toEntity(requestDTO, scheduleEvent, userOptional.get());

        // Lưu ScheduleEventReminder vào cơ sở dữ liệu
        scheduleEventReminder = scheduleEventReminderRepository.save(scheduleEventReminder);

        // Trả về ScheduleEventReminderResponseDTO
        return scheduleEventReminderMapper.toDto(scheduleEventReminder);
    }

    @Override
    public ScheduleEventReminderResponseDTO updateScheduleEventReminder(
            Long id, ScheduleEventReminderRequestDTO requestDTO) {
        // Tìm ScheduleEventReminder theo ID
        Optional<ScheduleEventReminder> scheduleEventReminderOptional = scheduleEventReminderRepository.findById(id);
        if (!scheduleEventReminderOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEventReminder not found!");
        }

        // Tìm ScheduleEvent và User theo ID
        Long scheduleEventId = Long.parseLong(requestDTO.getScheduleEventId());
        Optional<ScheduleEvent> scheduleEventOptional = scheduleEventRepository.findById(scheduleEventId);
        if (!scheduleEventOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEvent not found!");
        }

        Long userId = Long.parseLong(requestDTO.getUserId());
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found!");
        }

        // Kiểm tra remindAt có trước startTime của ScheduleEvent không
        ScheduleEvent scheduleEvent = scheduleEventOptional.get();
        if (requestDTO.getRemindAt().isAfter(scheduleEvent.getStartTime())) {
            throw new IllegalArgumentException("Reminder time (remindAt) must be before the start time of the event.");
        }

        // Cập nhật ScheduleEventReminder
        ScheduleEventReminder scheduleEventReminder = scheduleEventReminderOptional.get();
        scheduleEventReminder.setScheduleEvent(scheduleEvent);
        scheduleEventReminder.setUser(userOptional.get());
        scheduleEventReminder.setRemindAt(requestDTO.getRemindAt());

        // Lưu ScheduleEventReminder đã cập nhật vào cơ sở dữ liệu
        scheduleEventReminder = scheduleEventReminderRepository.save(scheduleEventReminder);

        // Trả về ScheduleEventReminderResponseDTO
        return scheduleEventReminderMapper.toDto(scheduleEventReminder);
    }

    @Override
    public void deleteScheduleEventReminder(Long id) {
        // Tìm ScheduleEventReminder theo ID và xóa
        Optional<ScheduleEventReminder> scheduleEventReminderOptional = scheduleEventReminderRepository.findById(id);
        if (!scheduleEventReminderOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEventReminder not found!");
        }
        scheduleEventReminderRepository.deleteById(id);
    }

    @Override
    public ScheduleEventReminderResponseDTO getScheduleEventReminder(Long id) {
        Optional<ScheduleEventReminder> scheduleEventReminderOptional = scheduleEventReminderRepository.findById(id);
        if (!scheduleEventReminderOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEventReminder not found!");
        }
        return scheduleEventReminderMapper.toDto(scheduleEventReminderOptional.get());
    }

    @Override
    public List<ScheduleEventReminderResponseDTO> getAllScheduleEventReminders() {
        List<ScheduleEventReminder> scheduleEventReminders = scheduleEventReminderRepository.findAll();
        return scheduleEventReminders.stream()
                .map(scheduleEventReminderMapper::toDto)
                .collect(Collectors.toList());
    }
}
