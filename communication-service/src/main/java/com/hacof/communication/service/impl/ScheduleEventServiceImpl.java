package com.hacof.communication.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        // Tìm Schedule theo scheduleId
        Long scheduleId = Long.parseLong(scheduleEventRequestDTO.getScheduleId());
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleId);

        if (!scheduleOptional.isPresent()) {
            throw new IllegalArgumentException("Schedule not found!");
        }

        // Chuyển từ ScheduleEventRequestDTO thành ScheduleEvent entity
        ScheduleEvent scheduleEvent = scheduleEventMapper.toEntity(scheduleEventRequestDTO, scheduleOptional.get());

        // Lưu ScheduleEvent vào cơ sở dữ liệu
        scheduleEvent = scheduleEventRepository.save(scheduleEvent);

        // Trả về ScheduleEventResponseDTO
        return scheduleEventMapper.toDto(scheduleEvent);
    }

    @Override
    public ScheduleEventResponseDTO updateScheduleEvent(Long id, ScheduleEventRequestDTO scheduleEventRequestDTO) {
        // Tìm ScheduleEvent theo ID
        Optional<ScheduleEvent> scheduleEventOptional = scheduleEventRepository.findById(id);
        if (!scheduleEventOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEvent not found!");
        }

        // Tìm Schedule theo scheduleId
        Long scheduleId = Long.parseLong(scheduleEventRequestDTO.getScheduleId());
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleId);
        if (!scheduleOptional.isPresent()) {
            throw new IllegalArgumentException("Schedule not found!");
        }

        // Cập nhật ScheduleEvent
        ScheduleEvent scheduleEvent = scheduleEventOptional.get();
        scheduleEvent.setSchedule(scheduleOptional.get());
        scheduleEvent.setName(scheduleEventRequestDTO.getName());
        scheduleEvent.setDescription(scheduleEventRequestDTO.getDescription());
        scheduleEvent.setLocation(scheduleEventRequestDTO.getLocation());
        scheduleEvent.setStartTime(scheduleEventRequestDTO.getStartTime());
        scheduleEvent.setEndTime(scheduleEventRequestDTO.getEndTime());
        scheduleEvent.setRecurring(scheduleEventRequestDTO.isRecurring());
        scheduleEvent.setRecurrenceRule(scheduleEventRequestDTO.getRecurrenceRule());

        // Lưu ScheduleEvent đã cập nhật vào cơ sở dữ liệu
        scheduleEvent = scheduleEventRepository.save(scheduleEvent);

        // Trả về ScheduleEventResponseDTO
        return scheduleEventMapper.toDto(scheduleEvent);
    }

    @Override
    public void deleteScheduleEvent(Long id) {
        // Tìm ScheduleEvent theo ID và xóa
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
}
