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
import com.hacof.hackathon.mapper.manual.HackathonResultMapperManual;
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

    @Override
    public HackathonResultDTO create(HackathonResultDTO hackathonResultDTO) {
        validateForeignKeys(hackathonResultDTO);
        HackathonResult hackathonResult = HackathonResultMapperManual.toEntity(hackathonResultDTO);

        hackathonResult.setHackathon(hackathonRepository
                .findById(Long.parseLong(hackathonResultDTO.getHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found")));

        hackathonResult.setTeam(teamRepository
                .findById(Long.parseLong(hackathonResultDTO.getTeamId()))
                .orElseThrow(() -> new ResourceNotFoundException("Team not found")));

        return HackathonResultMapperManual.toDto(hackathonResultRepository.save(hackathonResult));
    }

    @Override
    public HackathonResultDTO update(String id, HackathonResultDTO hackathonResultDTO) {
        validateForeignKeys(hackathonResultDTO);

        Authentication authentication = getAuthenticatedUser();

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

        HackathonResultMapperManual.updateEntityFromDto(hackathonResultDTO, existingResult);

        return HackathonResultMapperManual.toDto(hackathonResultRepository.save(existingResult));
    }

    @Override
    public void delete(Long id) {
        if (!hackathonResultRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hackathon result not found");
        }
        hackathonResultRepository.deleteById(id);
    }

    @Override
    public List<HackathonResultDTO> createBulk(List<HackathonResultDTO> hackathonResultDTOs) {
        hackathonResultDTOs.forEach(this::validateForeignKeys);

        List<HackathonResult> hackathonResults = hackathonResultDTOs.stream()
                .map(dto -> {
                    HackathonResult entity = HackathonResultMapperManual.toEntity(dto);

                    entity.setHackathon(hackathonRepository
                            .findById(Long.parseLong(dto.getHackathonId()))
                            .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found")));

                    entity.setTeam(teamRepository
                            .findById(Long.parseLong(dto.getTeamId()))
                            .orElseThrow(() -> new ResourceNotFoundException("Team not found")));

                    return entity;
                })
                .collect(Collectors.toList());

        return hackathonResultRepository.saveAll(hackathonResults).stream()
                .map(HackathonResultMapperManual::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HackathonResultDTO> updateBulk(List<HackathonResultDTO> hackathonResultDTOs) {
        hackathonResultDTOs.forEach(this::validateForeignKeys);

        Authentication authentication = getAuthenticatedUser();

        String username = authentication.getName();
        User currentUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        List<HackathonResult> updatedResults = hackathonResultDTOs.stream()
                .map(dto -> {
                    HackathonResult existing = getHackathonResult(dto.getId());

                    existing.setHackathon(hackathonRepository
                            .findById(Long.parseLong(dto.getHackathonId()))
                            .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found")));

                    existing.setTeam(teamRepository
                            .findById(Long.parseLong(dto.getTeamId()))
                            .orElseThrow(() -> new ResourceNotFoundException("Team not found")));

                    existing.setLastModifiedBy(currentUser);
                    existing.setLastModifiedDate(dto.getUpdatedAt());

                    HackathonResultMapperManual.updateEntityFromDto(dto, existing);

                    return existing;
                })
                .collect(Collectors.toList());

        return hackathonResultRepository.saveAll(updatedResults).stream()
                .map(HackathonResultMapperManual::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HackathonResultDTO> getAllByHackathonId(String hackathonId) {
        List<HackathonResult> results =
                hackathonResultRepository.findDetailedByHackathonId(Long.parseLong(hackathonId));
        return results.stream().map(HackathonResultMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public List<HackathonResultDTO> getAll() {
        List<HackathonResult> results = hackathonResultRepository.findAll();
        return results.stream().map(HackathonResultMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public HackathonResultDTO getById(Long id) {
        HackathonResult result = getHackathonResult(String.valueOf(id));
        return HackathonResultMapperManual.toDto(result);
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

    private Authentication getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }
        return authentication;
    }
}
