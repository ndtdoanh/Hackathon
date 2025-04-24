package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.MentorTeamDTO;
import com.hacof.hackathon.entity.MentorTeam;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.manual.MentorTeamMapperManual;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.MentorTeamRepository;
import com.hacof.hackathon.repository.TeamRepository;
import com.hacof.hackathon.repository.UserRepository;
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
    TeamRepository teamRepository;
    UserRepository userRepository;
    HackathonRepository hackathonRepository;

    // not use
    @Override
    public MentorTeamDTO create(MentorTeamDTO mentorTeamDTO) {
        //        validateForeignKeys(mentorTeamDTO);
        //        MentorTeam mentorTeam = mentorTeamMapper.toEntity(mentorTeamDTO);
        //
        //        // Manually set the foreign keys
        //        mentorTeam.setHackathon(hackathonRepository
        //                .findById(Long.parseLong(mentorTeamDTO.getHackathonId()))
        //                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found")));
        //        mentorTeam.setTeam(teamRepository
        //                .findById(Long.parseLong(mentorTeamDTO.getTeamId()))
        //                .orElseThrow(() -> new ResourceNotFoundException("Team not found")));
        //        mentorTeam.setMentor(userRepository
        //                .findById(Long.parseLong(mentorTeamDTO.getMentorId()))
        //                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found")));
        //
        //        return mentorTeamMapper.toDto(mentorTeamRepository.save(mentorTeam));
        return null;
    }

    // not use
    @Override
    public MentorTeamDTO update(String id, MentorTeamDTO mentorTeamDTO) {
        //        validateForeignKeys(mentorTeamDTO);
        //        MentorTeam existingMentorTeam = getMentorTeam(id);
        //
        //        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //        if (authentication == null || !authentication.isAuthenticated()) {
        //            throw new IllegalStateException("No authenticated user found");
        //        }
        //
        //        String username = authentication.getName();
        //        User currentUser = userRepository
        //                .findByUsername(username)
        //                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        //
        //        // Manually set the foreign keys
        //        existingMentorTeam.setHackathon(hackathonRepository
        //                .findById(Long.parseLong(mentorTeamDTO.getHackathonId()))
        //                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found")));
        //        existingMentorTeam.setTeam(teamRepository
        //                .findById(Long.parseLong(mentorTeamDTO.getTeamId()))
        //                .orElseThrow(() -> new ResourceNotFoundException("Team not found")));
        //        existingMentorTeam.setMentor(userRepository
        //                .findById(Long.parseLong(mentorTeamDTO.getMentorId()))
        //                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found")));
        //
        //        existingMentorTeam.setLastModifiedBy(currentUser);
        //        existingMentorTeam.setLastModifiedDate(mentorTeamDTO.getUpdatedAt());
        //        mentorTeamMapper.updateEntityFromDto(mentorTeamDTO, existingMentorTeam);
        //
        //        return mentorTeamMapper.toDto(mentorTeamRepository.save(existingMentorTeam));
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

    private void validateForeignKeys(MentorTeamDTO mentorTeamDTO) {
        if (!hackathonRepository.existsById(Long.parseLong(mentorTeamDTO.getHackathonId()))) {
            throw new ResourceNotFoundException("Hackathon not found");
        }
        if (!teamRepository.existsById(Long.parseLong(mentorTeamDTO.getTeamId()))) {
            throw new ResourceNotFoundException("Team not found");
        }
        if (!userRepository.existsById(Long.parseLong(mentorTeamDTO.getMentorId()))) {
            throw new ResourceNotFoundException("Mentor not found");
        }
    }

    private MentorTeam getMentorTeam(String id) {
        return mentorTeamRepository
                .findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("MentorTeam not found"));
    }
}
