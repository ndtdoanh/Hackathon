package com.hacof.communication.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.ScheduleRequestDTO;
import com.hacof.communication.dto.response.ScheduleResponseDTO;
import com.hacof.communication.entity.Hackathon;
import com.hacof.communication.entity.Schedule;
import com.hacof.communication.entity.Team;
import com.hacof.communication.mapper.ScheduleMapper;
import com.hacof.communication.repository.HackathonRepository;
import com.hacof.communication.repository.ScheduleRepository;
import com.hacof.communication.repository.TeamRepository;
import com.hacof.communication.service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private HackathonRepository hackathonRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public ScheduleResponseDTO createSchedule(ScheduleRequestDTO scheduleRequestDTO) {
        if (scheduleRequestDTO.getTeamId() == null) {
            throw new IllegalArgumentException("teamId must not be null");
        }

        Team team = teamRepository
                .findById(Long.parseLong(scheduleRequestDTO.getTeamId()))
                .orElseThrow(() -> new IllegalArgumentException("Team not found!"));

        if (scheduleRequestDTO.getHackathonId() == null) {
            throw new IllegalArgumentException("hackathonId must not be null");
        }
        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(scheduleRequestDTO.getHackathonId()))
                .orElseThrow(() -> new IllegalArgumentException("Hackathon not found!"));

        if (scheduleRequestDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        boolean scheduleExists = scheduleRepository.existsByTeamIdAndName(
                Long.parseLong(scheduleRequestDTO.getTeamId()), scheduleRequestDTO.getName());
        if (scheduleExists) {
            throw new IllegalArgumentException("A schedule with the same name already exists for this team.");
        }

        Schedule schedule = scheduleMapper.toEntity(scheduleRequestDTO, team, hackathon);
        schedule = scheduleRepository.save(schedule);

        return scheduleMapper.toDto(schedule);
    }

    @Override
    public ScheduleResponseDTO updateSchedule(Long id, ScheduleRequestDTO scheduleRequestDTO) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        if (!scheduleOptional.isPresent()) {
            throw new IllegalArgumentException("Schedule not found!");
        }

        if (scheduleRequestDTO.getTeamId() == null) {
            throw new IllegalArgumentException("teamId must not be null");
        }

        Team team = teamRepository
                .findById(Long.parseLong(scheduleRequestDTO.getTeamId()))
                .orElseThrow(() -> new IllegalArgumentException("Team not found!"));

        if (scheduleRequestDTO.getHackathonId() == null) {
            throw new IllegalArgumentException("hackathonId must not be null");
        }
        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(scheduleRequestDTO.getHackathonId()))
                .orElseThrow(() -> new IllegalArgumentException("Hackathon not found!"));

        if (scheduleRequestDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        boolean scheduleExists = scheduleRepository.existsByTeamIdAndNameAndIdNot(
                Long.parseLong(scheduleRequestDTO.getTeamId()), scheduleRequestDTO.getName(), id);
        if (scheduleExists) {
            throw new IllegalArgumentException("A schedule with the same name already exists for this team.");
        }

        Schedule schedule = scheduleOptional.get();
        schedule.setTeam(team);
        schedule.setHackathon(hackathon);
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

    @Override
    public List<ScheduleResponseDTO> getSchedulesByTeamId(Long teamId) {
        List<Schedule> schedules = scheduleRepository.findByTeamId(teamId);
        if (schedules.isEmpty()) {
            throw new IllegalArgumentException("No schedules found for the given teamId: " + teamId);
        }
        return schedules.stream().map(scheduleMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleResponseDTO> getSchedulesByCreatedByUsernameAndHackathonId(
            String createdByUsername, Long hackathonId) {
        List<Schedule> schedules =
                scheduleRepository.findByCreatedByUsernameAndHackathonId(createdByUsername, hackathonId);
        if (schedules.isEmpty()) {
            throw new IllegalArgumentException("No schedules found for the given createdByUsername and hackathonId.");
        }
        return schedules.stream().map(scheduleMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleResponseDTO> getSchedulesByTeamIdAndHackathonId(Long teamId, Long hackathonId) {
        List<Schedule> schedules = scheduleRepository.findByTeamIdAndHackathonId(teamId, hackathonId);
        if (schedules.isEmpty()) {
            throw new IllegalArgumentException("No schedules found for the given teamId and hackathonId.");
        }
        return schedules.stream().map(scheduleMapper::toDto).collect(Collectors.toList());
    }
}
