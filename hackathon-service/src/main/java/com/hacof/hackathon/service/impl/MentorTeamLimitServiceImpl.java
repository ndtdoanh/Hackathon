package com.hacof.hackathon.service.impl;

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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(dto.getHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));

        User mentor = userRepository
                .findById(Long.parseLong(dto.getMentorId()))
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));

        Team team = teamRepository
                .findById(Long.parseLong(dto.getTeamId()))
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

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

        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(dto.getHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));

        User mentor = userRepository
                .findById(Long.parseLong(dto.getMentorId()))
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));

        Team team = teamRepository
                .findById(Long.parseLong(dto.getTeamId()))
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

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
}
