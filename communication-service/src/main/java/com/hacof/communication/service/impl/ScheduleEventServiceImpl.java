package com.hacof.communication.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hacof.communication.constant.ScheduleEventStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.ScheduleEventRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventResponseDTO;
import com.hacof.communication.entity.Schedule;
import com.hacof.communication.entity.ScheduleEvent;
import com.hacof.communication.mapper.ScheduleEventMapper;
import com.hacof.communication.repository.ScheduleEventRepository;
import com.hacof.communication.repository.ScheduleRepository;
import com.hacof.communication.service.ScheduleEventService;

@Service
public class ScheduleEventServiceImpl implements ScheduleEventService {

    @Autowired
    private ScheduleEventRepository scheduleEventRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleEventMapper scheduleEventMapper;

    @Override
    public ScheduleEventResponseDTO createScheduleEvent(ScheduleEventRequestDTO scheduleEventRequestDTO) {
        if (scheduleEventRequestDTO.getScheduleId() == null) {
            throw new IllegalArgumentException("ScheduleId must not be null");
        }

        Schedule schedule = scheduleRepository.findById(Long.parseLong(scheduleEventRequestDTO.getScheduleId()))
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found!"));

        if (scheduleEventRequestDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty");
        }

        if (scheduleEventRequestDTO.getStartTime().isAfter(scheduleEventRequestDTO.getEndTime())) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }

        ScheduleEvent scheduleEvent = scheduleEventMapper.toEntity(scheduleEventRequestDTO, schedule);
        scheduleEvent = scheduleEventRepository.save(scheduleEvent);

        return scheduleEventMapper.toDto(scheduleEvent);
    }

    @Override
    public ScheduleEventResponseDTO updateScheduleEvent(Long id, ScheduleEventRequestDTO scheduleEventRequestDTO) {
        Optional<ScheduleEvent> scheduleEventOptional = scheduleEventRepository.findById(id);
        if (!scheduleEventOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEvent not found!");
        }

        if (scheduleEventRequestDTO.getScheduleId() == null) {
            throw new IllegalArgumentException("ScheduleId must not be null");
        }

        Schedule schedule = scheduleRepository.findById(Long.parseLong(scheduleEventRequestDTO.getScheduleId()))
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found!"));

        if (scheduleEventRequestDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty");
        }

        if (scheduleEventRequestDTO.getStartTime().isAfter(scheduleEventRequestDTO.getEndTime())) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }

        ScheduleEvent scheduleEvent = scheduleEventOptional.get();
        scheduleEvent.setSchedule(schedule);
        scheduleEvent.setName(scheduleEventRequestDTO.getName());
        scheduleEvent.setDescription(scheduleEventRequestDTO.getDescription());
        scheduleEvent.setLocation(scheduleEventRequestDTO.getLocation());
        scheduleEvent.setStartTime(scheduleEventRequestDTO.getStartTime());
        scheduleEvent.setEndTime(scheduleEventRequestDTO.getEndTime());
        scheduleEvent.setRecurring(scheduleEventRequestDTO.isRecurring());
        scheduleEvent.setRecurrenceRule(scheduleEventRequestDTO.getRecurrenceRule());

        scheduleEvent = scheduleEventRepository.save(scheduleEvent);
        return scheduleEventMapper.toDto(scheduleEvent);
    }

    @Override
    public void deleteScheduleEvent(Long id) {
        Optional<ScheduleEvent> scheduleEventOptional = scheduleEventRepository.findById(id);
        if (!scheduleEventOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEvent not found!");
        }
        scheduleEventRepository.deleteById(id);
    }

    @Override
    public ScheduleEventResponseDTO getScheduleEvent(Long id) {
        Optional<ScheduleEvent> scheduleEventOptional = scheduleEventRepository.findById(id);
        if (!scheduleEventOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEvent not found!");
        }
        return scheduleEventMapper.toDto(scheduleEventOptional.get());
    }

    @Override
    public List<ScheduleEventResponseDTO> getAllScheduleEvents() {
        List<ScheduleEvent> scheduleEvents = scheduleEventRepository.findAll();
        return scheduleEvents.stream().map(scheduleEventMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleEventResponseDTO> getScheduleEventsByScheduleId(Long scheduleId) {
        List<ScheduleEvent> scheduleEvents = scheduleEventRepository.findByScheduleId(scheduleId);
        if (scheduleEvents.isEmpty()) {
            throw new IllegalArgumentException("No schedule events found for the given scheduleId: " + scheduleId);
        }
        return scheduleEvents.stream()
                .map(scheduleEventMapper::toDto)
                .collect(Collectors.toList());
    }
}
