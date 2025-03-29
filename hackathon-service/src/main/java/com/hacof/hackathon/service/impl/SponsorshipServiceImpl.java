package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.SponsorshipDTO;
import com.hacof.hackathon.entity.Sponsorship;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.SponsorshipMapper;
import com.hacof.hackathon.repository.SponsorshipRepository;
import com.hacof.hackathon.service.SponsorshipService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SponsorshipServiceImpl implements SponsorshipService {
    private final SponsorshipRepository sponsorshipRepository;
    private final SponsorshipMapper sponsorshipMapper;

    @Override
    public SponsorshipDTO create(SponsorshipDTO sponsorshipDTO) {
        log.info("Creating new sponsorship");
        Sponsorship sponsorship = sponsorshipMapper.toEntity(sponsorshipDTO);
        sponsorship = sponsorshipRepository.save(sponsorship);
        return sponsorshipMapper.toDto(sponsorship);
    }

    @Override
    public SponsorshipDTO update(Long id, SponsorshipDTO sponsorshipDTO) {
        log.info("Updating sponsorship with id: {}", id);
        Sponsorship sponsorship = sponsorshipRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship not found"));
        sponsorshipMapper.updateEntityFromDto(sponsorshipDTO, sponsorship);
        sponsorship = sponsorshipRepository.save(sponsorship);
        return sponsorshipMapper.toDto(sponsorship);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting sponsorship with id: {}", id);
        if (!sponsorshipRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sponsorship not found");
        }
        sponsorshipRepository.deleteById(id);
    }

    @Override
    public List<SponsorshipDTO> getAll() {
        log.info("Fetching all sponsorships");
        return sponsorshipRepository.findAll().stream()
                .map(sponsorshipMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SponsorshipDTO getById(Long id) {
        log.info("Fetching sponsorship with id: {}", id);
        Sponsorship sponsorship = sponsorshipRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship not found"));
        return sponsorshipMapper.toDto(sponsorship);
    }
}
