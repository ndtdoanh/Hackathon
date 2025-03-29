package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
    public HackathonDTO create(HackathonDTO hackathonDTO) {
        Hackathon hackathon = hackathonMapper.toEntity(hackathonDTO);
        return hackathonMapper.toDto(hackathonRepository.save(hackathon));
    }

    @Override
    public HackathonDTO update(String id, HackathonDTO hackathonDTO) {
        log.debug("Updating hackathon with id: {}", id);
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        long Id;
        try {
            Id = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ID format: " + id);
        }

        Hackathon existingHackathon = hackathonRepository
                .findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found with id: " + Id));
        hackathonMapper.updateEntityFromDto(hackathonDTO, existingHackathon);
        return hackathonMapper.toDto(hackathonRepository.save(existingHackathon));
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
        if (hackathonRepository.findAll(spec).isEmpty()) {
            throw new ResourceNotFoundException("No hackathons found");
        }
        return hackathonRepository.findAll(spec).stream()
                .map(hackathonMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HackathonDTO> getHackathons() {
        if (hackathonRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No hackathons found");
        }
        return hackathonRepository.findAll().stream()
                .map(hackathonMapper::toDto)
                .collect(Collectors.toList());
    }
}
