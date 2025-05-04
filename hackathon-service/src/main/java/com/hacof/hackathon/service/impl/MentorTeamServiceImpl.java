package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.MentorTeamDTO;
import com.hacof.hackathon.entity.MentorTeam;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.manual.MentorTeamMapperManual;
import com.hacof.hackathon.repository.MentorTeamRepository;
import com.hacof.hackathon.service.MentorTeamService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@FieldDefaults(makeFinal = true)
public class MentorTeamServiceImpl implements MentorTeamService {
    MentorTeamRepository mentorTeamRepository;

    // not use
    @Override
    public MentorTeamDTO create(MentorTeamDTO mentorTeamDTO) {
        return null;
    }

    // not use
    @Override
    public MentorTeamDTO update(String id, MentorTeamDTO mentorTeamDTO) {
        return null;
    }

    // not use
    @Override
    public void delete(String id) {
        if (!mentorTeamRepository.existsById(Long.parseLong(id))) {
            throw new ResourceNotFoundException("MentorTeam not found");
        }
        mentorTeamRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public List<MentorTeamDTO> getAllByHackathonIdAndTeamId(String hackathonId, String teamId) {
        List<MentorTeam> mentorTeams =
                mentorTeamRepository.findAllByHackathonIdAndTeamId(Long.parseLong(hackathonId), Long.parseLong(teamId));
        return mentorTeams.stream().map(MentorTeamMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public List<MentorTeamDTO> getAllByMentorId(String mentorId) {
        List<MentorTeam> mentorTeams = mentorTeamRepository.findAllByMentorId(Long.parseLong(mentorId));
        return mentorTeams.stream().map(MentorTeamMapperManual::toDto).collect(Collectors.toList());
    }

}
