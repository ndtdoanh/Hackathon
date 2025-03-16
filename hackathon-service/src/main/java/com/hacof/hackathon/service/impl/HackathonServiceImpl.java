package com.hacof.hackathon.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.HackathonMapper;
import com.hacof.hackathon.mapper.RoundMapper;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.service.HackathonService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class HackathonServiceImpl implements HackathonService {
    final HackathonRepository hackathonRepository;
    final RoundMapper roundMapper;
    final HackathonMapper hackathonMapper;

    @Override
    public List<HackathonDTO> getByAllCriteria(Specification<Hackathon> spec) {
        List<Hackathon> hackathons = hackathonRepository.findAll(spec);
        if (hackathons.isEmpty()) {
            log.warn("No hackathon found");
            throw new ResourceNotFoundException("No hackathon found");
        }

        return hackathons.stream()
                .map(hackathon -> {
                    HackathonDTO dto = hackathonMapper.convertToDTO(hackathon);
                    List<RoundDTO> roundDTOs =
                            Optional.ofNullable(hackathon.getRounds()).orElse(Collections.emptyList()).stream()
                                    .map(roundMapper::convertToDTO)
                                    .collect(Collectors.toList());
                    dto.setRounds(roundDTOs);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public HackathonDTO createHackathon(HackathonDTO hackathonDTO) {
        Hackathon hackathon = hackathonMapper.convertToEntity(hackathonDTO);
        Hackathon savedHackathon = hackathonRepository.save(hackathon);
        return hackathonMapper.convertToDTO(savedHackathon);
    }

    @Override
    public HackathonDTO updateHackathon(Long id, HackathonDTO hackathonDTO) {
        if (!hackathonRepository.existsById(id)) {
            log.warn("Hackathon not found");
            throw new ResourceNotFoundException("Hackathon not found");
        }
        Hackathon existingHackathon = hackathonRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));
        existingHackathon.setName(hackathonDTO.getName());
        existingHackathon.setBannerImageUrl(hackathonDTO.getBannerImageUrl());
        existingHackathon.setDescription(hackathonDTO.getDescription());
        existingHackathon.setStartDate(hackathonDTO.getStartDate());
        existingHackathon.setEndDate(hackathonDTO.getEndDate());
        existingHackathon.setNumberRound(hackathonDTO.getNumberRound());
        existingHackathon.setMaxTeams(hackathonDTO.getMaxTeams());
        existingHackathon.setMinTeamSize(hackathonDTO.getMinTeamSize());
        existingHackathon.setMaxTeamSize(hackathonDTO.getMaxTeamSize());
        existingHackathon.setStatus(hackathonDTO.getStatus());

        Hackathon updatedHackathon = hackathonRepository.save(existingHackathon);
        return hackathonMapper.convertToDTO(updatedHackathon);
    }

    @Override
    public void deleteHackathon(Long id) {
        if (!hackathonRepository.existsById(id)) {
            log.warn("Hackathon not found with id {}", id);
            throw new ResourceNotFoundException("Hackathon not found");
        }
        Hackathon hackathon = hackathonRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));
        hackathonRepository.delete(hackathon);
    }

    @Override
    public List<HackathonDTO> getAllHackathon(Specification<Hackathon> spec) {
        List<Hackathon> hackathons = hackathonRepository.findAll(spec);
        return hackathons.stream().map(hackathonMapper::convertToDTO).collect(Collectors.toList());
    }
}
