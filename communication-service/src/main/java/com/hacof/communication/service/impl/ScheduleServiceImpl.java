package com.hacof.communication.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.ScheduleRequestDTO;
import com.hacof.communication.dto.response.ScheduleResponseDTO;
import com.hacof.communication.entity.Schedule;
import com.hacof.communication.entity.Team;
import com.hacof.communication.mapper.ScheduleMapper;
import com.hacof.communication.repository.ScheduleRepository;
import com.hacof.communication.repository.TeamRepository;
import com.hacof.communication.service.ScheduleService;

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
        Long teamId = Long.parseLong(scheduleRequestDTO.getTeamId());
        Optional<Team> teamOptional = teamRepository.findById(teamId);

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

        Long teamId = Long.parseLong(scheduleRequestDTO.getTeamId());
        Optional<Team> teamOptional = teamRepository.findById(teamId);
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

    @Override
    public void deleteSchedule(Long id) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        if (!scheduleOptional.isPresent()) {
            throw new IllegalArgumentException("Schedule not found!");
        }
        scheduleRepository.deleteById(id);
    }

    @Override
    public ScheduleResponseDTO getSchedule(Long id) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        if (!scheduleOptional.isPresent()) {
            throw new IllegalArgumentException("Schedule not found!");
        }

        return scheduleMapper.toDto(scheduleOptional.get());
    }

    @Override
    public List<ScheduleResponseDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream().map(scheduleMapper::toDto).collect(Collectors.toList());
    }
}
