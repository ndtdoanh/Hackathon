package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.SponsorshipHackathonDTO;
import com.hacof.hackathon.entity.SponsorshipHackathon;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.SponsorshipHackathonMapper;
import com.hacof.hackathon.repository.SponsorshipHackathonRepository;
import com.hacof.hackathon.service.SponsorshipHackathonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SponsorshipHackathonServiceImpl implements SponsorshipHackathonService {
    private final SponsorshipHackathonRepository sponsorshipHackathonRepository;
    private final SponsorshipHackathonMapper sponsorshipHackathonMapper;

    @Override
    public SponsorshipHackathonDTO create(SponsorshipHackathonDTO sponsorshipHackathonDTO) {
        log.info("Creating new sponsorship hackathon");
        SponsorshipHackathon sponsorshipHackathon = sponsorshipHackathonMapper.toEntity(sponsorshipHackathonDTO);
        sponsorshipHackathon = sponsorshipHackathonRepository.save(sponsorshipHackathon);
        return sponsorshipHackathonMapper.toDto(sponsorshipHackathon);
    }

    @Override
    public SponsorshipHackathonDTO update(Long id, SponsorshipHackathonDTO sponsorshipHackathonDTO) {
        log.info("Updating sponsorship hackathon with id: {}", id);
        SponsorshipHackathon sponsorshipHackathon = sponsorshipHackathonRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship hackathon not found"));
        sponsorshipHackathonMapper.updateEntityFromDto(sponsorshipHackathonDTO, sponsorshipHackathon);
        sponsorshipHackathon = sponsorshipHackathonRepository.save(sponsorshipHackathon);
        return sponsorshipHackathonMapper.toDto(sponsorshipHackathon);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting sponsorship hackathon with id: {}", id);
        if (!sponsorshipHackathonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sponsorship hackathon not found");
        }
        sponsorshipHackathonRepository.deleteById(id);
    }

    @Override
    public List<SponsorshipHackathonDTO> getAll() {
        log.info("Fetching all sponsorship hackathons");
        return sponsorshipHackathonRepository.findAll().stream()
                .map(sponsorshipHackathonMapper::toDto)
                .collect(Collectors.toList());
    }
}
