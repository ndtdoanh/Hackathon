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
        Optional<ScheduleEvent> scheduleEventOptional =
                scheduleEventRepository.findById(Long.parseLong(requestDTO.getScheduleEventId()));
        if (!scheduleEventOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEvent not found!");
        }

        Optional<User> userOptional = userRepository.findById(Long.parseLong(requestDTO.getUserId()));
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found!");
        }

        ScheduleEventAttendee scheduleEventAttendee =
                scheduleEventAttendeeMapper.toEntity(requestDTO, scheduleEventOptional.get(), userOptional.get());
        scheduleEventAttendee = scheduleEventAttendeeRepository.save(scheduleEventAttendee);

        return scheduleEventAttendeeMapper.toDto(scheduleEventAttendee);
    }

    @Override
    public ScheduleEventAttendeeResponseDTO updateScheduleEventAttendee(
            Long id, ScheduleEventAttendeeRequestDTO requestDTO) {
        Optional<ScheduleEventAttendee> scheduleEventAttendeeOptional = scheduleEventAttendeeRepository.findById(id);
        if (!scheduleEventAttendeeOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEventAttendee not found!");
        }

        Optional<ScheduleEvent> scheduleEventOptional =
                scheduleEventRepository.findById(Long.parseLong(requestDTO.getScheduleEventId()));
        if (!scheduleEventOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEvent not found!");
        }

        Optional<User> userOptional = userRepository.findById(Long.parseLong(requestDTO.getUserId()));
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found!");
        }

        ScheduleEventAttendee scheduleEventAttendee = scheduleEventAttendeeOptional.get();
        scheduleEventAttendee.setScheduleEvent(scheduleEventOptional.get());
        scheduleEventAttendee.setUser(userOptional.get());
        scheduleEventAttendee = scheduleEventAttendeeRepository.save(scheduleEventAttendee);

        return scheduleEventAttendeeMapper.toDto(scheduleEventAttendee);
    }

    @Override
    public void deleteScheduleEventAttendee(Long id) {
        // Tìm ScheduleEventAttendee theo ID và xóa
        Optional<ScheduleEventAttendee> scheduleEventAttendeeOptional = scheduleEventAttendeeRepository.findById(id);
        if (!scheduleEventAttendeeOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEventAttendee not found!");
        }
        scheduleEventAttendeeRepository.deleteById(id);
    }

    @Override
    public ScheduleEventAttendeeResponseDTO getScheduleEventAttendee(Long id) {
        Optional<ScheduleEventAttendee> scheduleEventAttendeeOptional = scheduleEventAttendeeRepository.findById(id);
        if (!scheduleEventAttendeeOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEventAttendee not found!");
        }
        return scheduleEventAttendeeMapper.toDto(scheduleEventAttendeeOptional.get());
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
}
