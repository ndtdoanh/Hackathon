package com.hacof.communication.service.impl;

import com.hacof.communication.dto.request.ScheduleRequestDTO;
import com.hacof.communication.dto.response.ScheduleResponseDTO;
import com.hacof.communication.entity.Schedule;
import com.hacof.communication.entity.Team;
import com.hacof.communication.mapper.ScheduleMapper;
import com.hacof.communication.repository.ScheduleRepository;
import com.hacof.communication.repository.TeamRepository;
import com.hacof.communication.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public ScheduleResponseDTO createSchedule(ScheduleRequestDTO scheduleRequestDTO) {
        Optional<Team> teamOptional = teamRepository.findById(scheduleRequestDTO.getTeamId());
        if (!teamOptional.isPresent()) {
            throw new IllegalArgumentException("Team not found!");
        }

        Schedule schedule = scheduleMapper.toEntity(scheduleRequestDTO, teamOptional.get());
        schedule = scheduleRepository.save(schedule);

        return scheduleMapper.toDto(schedule);
    }

    @Override
    public ScheduleResponseDTO updateSchedule(Long id, ScheduleRequestDTO scheduleRequestDTO) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        if (!scheduleOptional.isPresent()) {
            throw new IllegalArgumentException("Schedule not found!");
        }

        Optional<Team> teamOptional = teamRepository.findById(scheduleRequestDTO.getTeamId());
        if (!teamOptional.isPresent()) {
            throw new IllegalArgumentException("Team not found!");
        }

        Schedule schedule = scheduleOptional.get();
        schedule.setTeam(teamOptional.get());
        schedule.setName(scheduleRequestDTO.getName());
        schedule.setDescription(scheduleRequestDTO.getDescription());
        schedule = scheduleRepository.save(schedule);

        return scheduleMapper.toDto(schedule);
    }

}
