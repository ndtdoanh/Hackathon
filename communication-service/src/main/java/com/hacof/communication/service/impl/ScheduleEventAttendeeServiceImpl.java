package com.hacof.communication.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.constant.ScheduleEventStatus;
import com.hacof.communication.dto.request.ScheduleEventAttendeeRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventAttendeeResponseDTO;
import com.hacof.communication.entity.ScheduleEvent;
import com.hacof.communication.entity.ScheduleEventAttendee;
import com.hacof.communication.entity.User;
import com.hacof.communication.mapper.ScheduleEventAttendeeMapper;
import com.hacof.communication.repository.ScheduleEventAttendeeRepository;
import com.hacof.communication.repository.ScheduleEventRepository;
import com.hacof.communication.repository.UserRepository;
import com.hacof.communication.service.ScheduleEventAttendeeService;

@Service
public class ScheduleEventAttendeeServiceImpl implements ScheduleEventAttendeeService {

    @Autowired
    private ScheduleEventAttendeeRepository scheduleEventAttendeeRepository;

    @Autowired
    private ScheduleEventRepository scheduleEventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleEventAttendeeMapper scheduleEventAttendeeMapper;

    @Override
    public ScheduleEventAttendeeResponseDTO createScheduleEventAttendee(ScheduleEventAttendeeRequestDTO requestDTO) {
        if (requestDTO.getScheduleEventId() == null) {
            throw new IllegalArgumentException("scheduleEventId must not be null");
        }

        boolean exists = scheduleEventAttendeeRepository.existsByScheduleEventIdAndUserId(
                Long.parseLong(requestDTO.getScheduleEventId()), Long.parseLong(requestDTO.getUserId()));

        if (exists) {
            throw new IllegalArgumentException("User is already assigned to this schedule event.");
        }

        ScheduleEvent scheduleEvent = scheduleEventRepository
                .findById(Long.parseLong(requestDTO.getScheduleEventId()))
                .orElseThrow(() -> new IllegalArgumentException("ScheduleEvent not found!"));

        User user = userRepository
                .findById(Long.parseLong(requestDTO.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        ScheduleEventAttendee attendee = scheduleEventAttendeeMapper.toEntity(requestDTO, scheduleEvent, user);
        attendee = scheduleEventAttendeeRepository.save(attendee);

        return scheduleEventAttendeeMapper.toDto(attendee);
    }

    @Override
    public ScheduleEventAttendeeResponseDTO updateScheduleEventAttendee(
            Long id, ScheduleEventAttendeeRequestDTO requestDTO) {
        Optional<ScheduleEventAttendee> scheduleEventAttendeeOptional = scheduleEventAttendeeRepository.findById(id);
        if (!scheduleEventAttendeeOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEventAttendee not found!");
        }

        if (requestDTO.getScheduleEventId() == null) {
            throw new IllegalArgumentException("scheduleEventId must not be null");
        }

        ScheduleEvent scheduleEvent = scheduleEventRepository
                .findById(Long.parseLong(requestDTO.getScheduleEventId()))
                .orElseThrow(() -> new IllegalArgumentException("ScheduleEvent not found!"));

        User user = userRepository
                .findById(Long.parseLong(requestDTO.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        ScheduleEventAttendee scheduleEventAttendee = scheduleEventAttendeeOptional.get();
        scheduleEventAttendee.setScheduleEvent(scheduleEvent);
        scheduleEventAttendee.setUser(user);
        scheduleEventAttendee = scheduleEventAttendeeRepository.save(scheduleEventAttendee);

        return scheduleEventAttendeeMapper.toDto(scheduleEventAttendee);
    }

    @Override
    public void deleteScheduleEventAttendee(Long id) {
        Optional<ScheduleEventAttendee> scheduleEventAttendeeOptional = scheduleEventAttendeeRepository.findById(id);
        if (!scheduleEventAttendeeOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEventAttendee not found!");
        }
        scheduleEventAttendeeRepository.deleteById(id);
    }

    @Override
    public List<ScheduleEventAttendeeResponseDTO> getScheduleEventAttendee(Long id) {
        Optional<ScheduleEventAttendee> scheduleEventAttendeeOptional = scheduleEventAttendeeRepository.findById(id);

        return scheduleEventAttendeeOptional
                .map(attendee -> List.of(scheduleEventAttendeeMapper.toDto(attendee)))
                .orElse(List.of());
    }


    @Override
    public List<ScheduleEventAttendeeResponseDTO> getAllScheduleEventAttendees() {
        List<ScheduleEventAttendee> scheduleEventAttendees = scheduleEventAttendeeRepository.findAll();
        return scheduleEventAttendees.stream()
                .map(scheduleEventAttendeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleEventAttendeeResponseDTO changeStatus(Long id, ScheduleEventStatus status) {
        Optional<ScheduleEventAttendee> scheduleEventAttendeeOptional = scheduleEventAttendeeRepository.findById(id);
        if (!scheduleEventAttendeeOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEventAttendee not found!");
        }

        ScheduleEventAttendee scheduleEventAttendee = scheduleEventAttendeeOptional.get();
        scheduleEventAttendee.setStatus(status);
        scheduleEventAttendee = scheduleEventAttendeeRepository.save(scheduleEventAttendee);

        return scheduleEventAttendeeMapper.toDto(scheduleEventAttendee);
    }

    @Override
    public List<ScheduleEventAttendeeResponseDTO> getScheduleEventAttendeesByEventId(Long scheduleEventId) {
        List<ScheduleEventAttendee> attendees = scheduleEventAttendeeRepository.findByScheduleEventId(scheduleEventId);
        return attendees.isEmpty() ? List.of() : attendees.stream()
                .map(scheduleEventAttendeeMapper::toDto)
                .collect(Collectors.toList());
    }
}
