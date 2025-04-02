package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.MentorTeamLimitDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.MentorTeamLimit;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.MentorTeamLimitMapper;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.MentorTeamLimitRepository;
import com.hacof.hackathon.repository.TeamRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.MentorTeamLimitService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class MentorTeamLimitServiceImpl implements MentorTeamLimitService {
    MentorTeamLimitRepository mentorTeamLimitRepository;
    HackathonRepository hackathonRepository;
    UserRepository userRepository;
    TeamRepository teamRepository;
    MentorTeamLimitMapper mentorTeamLimitMapper;

    @Override
    public MentorTeamLimitDTO create(MentorTeamLimitDTO mentorTeamLimitDTO) {
        log.info("Creating new mentor team limit");

        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(mentorTeamLimitDTO.getHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));

        User mentor = userRepository
                .findById(Long.parseLong(mentorTeamLimitDTO.getMentorId()))
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));

        Team team = teamRepository
                .findById(Long.parseLong(mentorTeamLimitDTO.getTeamId()))
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        MentorTeamLimit mentorTeamLimit = mentorTeamLimitMapper.toEntity(mentorTeamLimitDTO);
        mentorTeamLimit.setHackathon(hackathon);
        mentorTeamLimit.setMentor(mentor);
        mentorTeamLimit.setTeam(team);

        mentorTeamLimit = mentorTeamLimitRepository.save(mentorTeamLimit);
        return mentorTeamLimitMapper.toDto(mentorTeamLimit);
    }

    @Override
    public MentorTeamLimitDTO update(Long id, MentorTeamLimitDTO mentorTeamLimitDTO) {
        log.info("Updating mentor team limit with id: {}", id);

        MentorTeamLimit mentorTeamLimit = mentorTeamLimitRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor team limit not found"));

        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(mentorTeamLimitDTO.getHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));

        User mentor = userRepository
                .findById(Long.parseLong(mentorTeamLimitDTO.getMentorId()))
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));

        Team team = teamRepository
                .findById(Long.parseLong(mentorTeamLimitDTO.getTeamId()))
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        mentorTeamLimitMapper.updateEntityFromDto(mentorTeamLimitDTO, mentorTeamLimit);
        mentorTeamLimit.setHackathon(hackathon);
        mentorTeamLimit.setMentor(mentor);
        mentorTeamLimit.setTeam(team);

        mentorTeamLimit = mentorTeamLimitRepository.save(mentorTeamLimit);
        return mentorTeamLimitMapper.toDto(mentorTeamLimit);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting mentor team limit with id: {}", id);
        if (!mentorTeamLimitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Mentor team limit not found");
        }
        mentorTeamLimitRepository.deleteById(id);
    }

    @Override
    public List<MentorTeamLimitDTO> getAll() {
        log.info("Fetching all mentor team limits");
        if (mentorTeamLimitRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No mentor team limits found");
        }
        return mentorTeamLimitRepository.findAll().stream()
                .map(mentorTeamLimitMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MentorTeamLimitDTO getById(Long id) {
        log.info("Fetching mentor team limit with id: {}", id);
        MentorTeamLimit mentorTeamLimit = mentorTeamLimitRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor team limit not found"));
        return mentorTeamLimitMapper.toDto(mentorTeamLimit);
    }
}
