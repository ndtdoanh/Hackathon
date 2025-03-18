package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.HackathonMapper;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.service.HackathonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class HackathonServiceImpl implements HackathonService {
    final HackathonRepository hackathonRepository;
    final HackathonMapper hackathonMapper;

    @Override
    public HackathonDTO createHackathon(HackathonDTO hackathonDTO) {
        log.info("Creating new hackathon: {}", hackathonDTO);
        Hackathon hackathon = hackathonMapper.toEntity(hackathonDTO);

        // Set initial status if not provided
        if (hackathon.getStatus() == null) {
            hackathon.setStatus(Status.DRAFT);
        }

        // Save
        hackathon = hackathonRepository.save(hackathon);
        log.info("Created new hackathon with id: {}", hackathon.getId());

        return hackathonMapper.toDTO(hackathon);
    }

    @Override
    public HackathonDTO updateHackathon(Long id, HackathonDTO hackathonDTO) {
        log.info("Updating hackathon with id {}: {}", id, hackathonDTO);

        // Find existing hackathon
        Hackathon hackathon = hackathonRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found with id: " + id));

        // Update basic fields
        hackathon.setName(hackathonDTO.getName());
        hackathon.setDescription(hackathonDTO.getDescription());
        hackathon.setStartDate(hackathonDTO.getStartDate());
        hackathon.setEndDate(hackathonDTO.getEndDate());
        hackathon.setNumberRound(hackathonDTO.getNumberRound());
        hackathon.setMaxTeams(hackathonDTO.getMaxTeams());
        hackathon.setMinTeamSize(hackathonDTO.getMinTeamSize());
        hackathon.setMaxTeamSize(hackathonDTO.getMaxTeamSize());
        hackathon.setStatus(Status.valueOf(hackathonDTO.getStatus()));
        hackathon.setBannerImageUrl(hackathonDTO.getBannerImageUrl());

        // Save
        hackathon = hackathonRepository.save(hackathon);
        log.info("Updated hackathon with id: {}", hackathon.getId());

        return hackathonMapper.toDTO(hackathon);
    }

    @Override
    public void deleteHackathon(Long id) {
        log.info("Deleting hackathon with id: {}", id);

        // Check if exists
        if (!hackathonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hackathon not found with id: " + id);
        }

        // Delete
        hackathonRepository.deleteById(id);
        log.info("Deleted hackathon with id: {}", id);
    }

    @Override
    public List<HackathonDTO> getHackathons(Specification<Hackathon> spec) {
        log.info("Fetching hackathons with specification");
        if (hackathonRepository.findAll(spec).isEmpty()) {
            throw new ResourceNotFoundException("Hackathon not found");
        }

        return hackathonRepository.findAll(spec).stream()
                .map(hackathonMapper::toDTO)
                .collect(Collectors.toList());
    }
}
