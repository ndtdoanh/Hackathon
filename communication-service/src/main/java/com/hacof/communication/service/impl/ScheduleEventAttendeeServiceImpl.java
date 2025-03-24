package com.hacof.communication.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Optional<ScheduleEvent> scheduleEventOptional = scheduleEventRepository.findById(requestDTO.getScheduleEventId());
        if (!scheduleEventOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEvent not found!");
        }

        Optional<User> userOptional = userRepository.findById(requestDTO.getUserId());
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found!");
        }

        ScheduleEventAttendee scheduleEventAttendee = scheduleEventAttendeeMapper.toEntity(requestDTO, scheduleEventOptional.get(), userOptional.get());
        scheduleEventAttendee = scheduleEventAttendeeRepository.save(scheduleEventAttendee);

        return scheduleEventAttendeeMapper.toDto(scheduleEventAttendee);
    }
}
