package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.HackathonResultDTO;
import com.hacof.hackathon.entity.HackathonResult;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.HackathonResultMapper;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.HackathonResultRepository;
import com.hacof.hackathon.repository.TeamRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.HackathonResultService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@FieldDefaults(makeFinal = true)
public class HackathonResultServiceImpl implements HackathonResultService {
    HackathonResultRepository hackathonResultRepository;
    TeamRepository teamRepository;
    UserRepository userRepository;
    HackathonRepository hackathonRepository;
    HackathonResultMapper hackathonResultMapper;

    @Override
    public HackathonResultDTO create(HackathonResultDTO hackathonResultDTO) {
        validateForeignKeys(hackathonResultDTO);
        HackathonResult hackathonResult = hackathonResultMapper.toEntity(hackathonResultDTO);
        hackathonResult.setHackathon(hackathonRepository
                .findById(Long.parseLong(hackathonResultDTO.getHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found")));
        hackathonResult.setTeam(teamRepository
                .findById(Long.parseLong(hackathonResultDTO.getTeamId()))
                .orElseThrow(() -> new ResourceNotFoundException("Team not found")));

        return hackathonResultMapper.toDto(hackathonResultRepository.save(hackathonResult));
    }

    @Override
    public HackathonResultDTO update(String id, HackathonResultDTO hackathonResultDTO) {
        validateForeignKeys(hackathonResultDTO);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }

        String username = authentication.getName();
        User currentUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        HackathonResult existingResult = getHackathonResult(id);
        existingResult.setHackathon(hackathonRepository
                .findById(Long.parseLong(hackathonResultDTO.getHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found")));
        existingResult.setTeam(teamRepository
                .findById(Long.parseLong(hackathonResultDTO.getTeamId()))
                .orElseThrow(() -> new ResourceNotFoundException("Team not found")));

        existingResult.setLastModifiedBy(currentUser);
        existingResult.setCreatedBy(currentUser);
        existingResult.setLastModifiedDate(hackathonResultDTO.getUpdatedAt());
        hackathonResultMapper.updateEntityFromDto(hackathonResultDTO, existingResult);
        return hackathonResultMapper.toDto(hackathonResultRepository.save(existingResult));
    }

    @Override
    public void delete(String id) {
        if (!hackathonResultRepository.existsById(Long.parseLong(id))) {
            throw new ResourceNotFoundException("Hackathon result not found");
        }
        hackathonResultRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public List<HackathonResultDTO> createBulk(List<HackathonResultDTO> hackathonResultDTOs) {
        hackathonResultDTOs.forEach(this::validateForeignKeys);
        List<HackathonResult> hackathonResults = hackathonResultDTOs.stream()
                .map(dto -> {
                    HackathonResult hackathonResult = hackathonResultMapper.toEntity(dto);
                    hackathonResult.setHackathon(hackathonRepository
                            .findById(Long.parseLong(dto.getHackathonId()))
                            .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found")));
                    hackathonResult.setTeam(teamRepository
                            .findById(Long.parseLong(dto.getTeamId()))
                            .orElseThrow(() -> new ResourceNotFoundException("Team not found")));
                    return hackathonResult;
                })
                .collect(Collectors.toList());
        return hackathonResultRepository.saveAll(hackathonResults).stream()
                .map(hackathonResultMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HackathonResultDTO> updateBulk(List<HackathonResultDTO> hackathonResultDTOs) {
        hackathonResultDTOs.forEach(this::validateForeignKeys);
        List<HackathonResult> hackathonResults = hackathonResultDTOs.stream()
                .map(dto -> {
                    HackathonResult existingResult = getHackathonResult(dto.getId());
                    Authentication authentication =
                            SecurityContextHolder.getContext().getAuthentication();
                    if (authentication == null || !authentication.isAuthenticated()) {
                        throw new IllegalStateException("No authenticated user found");
                    }

                    String username = authentication.getName();
                    User currentUser = userRepository
                            .findByUsername(username)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

                    existingResult.setHackathon(hackathonRepository
                            .findById(Long.parseLong(dto.getHackathonId()))
                            .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found")));
                    existingResult.setTeam(teamRepository
                            .findById(Long.parseLong(dto.getTeamId()))
                            .orElseThrow(() -> new ResourceNotFoundException("Team not found")));
                    existingResult.setLastModifiedDate(dto.getUpdatedAt());
                    existingResult.setLastModifiedBy(currentUser);
                    hackathonResultMapper.updateEntityFromDto(dto, existingResult);
                    return existingResult;
                })
                .collect(Collectors.toList());
        return hackathonResultRepository.saveAll(hackathonResults).stream()
                .map(hackathonResultMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HackathonResultDTO> getAllByHackathonId(String hackathonId) {
        List<HackathonResult> results = hackathonResultRepository.findByHackathonId(Long.parseLong(hackathonId));
        return results.stream().map(hackathonResultMapper::toDto).collect(Collectors.toList());
    }

    private void validateForeignKeys(HackathonResultDTO hackathonResultDTO) {
        if (!hackathonRepository.existsById(Long.parseLong(hackathonResultDTO.getHackathonId()))) {
            throw new ResourceNotFoundException("Hackathon not found");
        }
        if (!teamRepository.existsById(Long.parseLong(hackathonResultDTO.getTeamId()))) {
            throw new ResourceNotFoundException("Team not found");
        }
    }

    private HackathonResult getHackathonResult(String id) {
        return hackathonResultRepository
                .findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon result not found"));
    }
}
