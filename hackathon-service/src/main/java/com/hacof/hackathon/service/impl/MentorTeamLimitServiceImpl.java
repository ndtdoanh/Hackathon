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
import com.hacof.hackathon.mapper.manual.MentorTeamLimitMapperManual;
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
    TeamRepository teamRepository;
    UserRepository userRepository;

    @Override
    public MentorTeamLimitDTO create(MentorTeamLimitDTO dto) {
        Hackathon hackathon = fetchHackathon(dto.getHackathonId());
        User mentor = fetchMentor(dto.getMentorId());
        Team team = fetchTeam(dto.getTeamId());

        MentorTeamLimit entity = MentorTeamLimitMapperManual.toEntity(dto);
        entity.setHackathon(hackathon);
        entity.setMentor(mentor);
        entity.setTeam(team);

        entity = mentorTeamLimitRepository.save(entity);
        return MentorTeamLimitMapperManual.toDto(entity);
    }

    @Override
    public MentorTeamLimitDTO update(Long id, MentorTeamLimitDTO dto) {
        MentorTeamLimit entity = mentorTeamLimitRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor team limit not found"));

        Hackathon hackathon = fetchHackathon(dto.getHackathonId());
        User mentor = fetchMentor(dto.getMentorId());
        Team team = fetchTeam(dto.getTeamId());

        // Update fields
        entity.setHackathon(hackathon);
        entity.setMentor(mentor);
        entity.setTeam(team);
        entity.setMaxTeams(dto.getMaxTeams());
        entity.setMaxMentors(dto.getMaxMentors());

        entity = mentorTeamLimitRepository.save(entity);
        return MentorTeamLimitMapperManual.toDto(entity);
    }

    @Override
    public void delete(Long id) {
        if (!mentorTeamLimitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Mentor team limit not found");
        }
        mentorTeamLimitRepository.deleteById(id);
    }

    @Override
    public List<MentorTeamLimitDTO> getAll() {
        List<MentorTeamLimit> list = mentorTeamLimitRepository.findAll();
        return list.stream().map(MentorTeamLimitMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public MentorTeamLimitDTO getById(Long id) {
        MentorTeamLimit entity = mentorTeamLimitRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor team limit not found"));
        return MentorTeamLimitMapperManual.toDto(entity);
    }

    private Hackathon fetchHackathon(String hackathonId) {
        return hackathonRepository
                .findById(Long.parseLong(hackathonId))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));
    }

    private User fetchMentor(String mentorId) {
        return userRepository
                .findById(Long.parseLong(mentorId))
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));
    }

    private Team fetchTeam(String teamId) {
        return teamRepository
                .findById(Long.parseLong(teamId))
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
    }
}
