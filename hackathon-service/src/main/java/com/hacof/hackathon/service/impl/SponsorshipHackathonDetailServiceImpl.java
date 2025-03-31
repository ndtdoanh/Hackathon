package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.SponsorshipHackathonDetailDTO;
import com.hacof.hackathon.entity.SponsorshipHackathonDetail;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.SponsorshipHackathonDetailMapper;
import com.hacof.hackathon.repository.SponsorshipHackathonDetailRepository;
import com.hacof.hackathon.service.SponsorshipHackathonDetailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SponsorshipHackathonDetailServiceImpl implements SponsorshipHackathonDetailService {
    private final SponsorshipHackathonDetailRepository sponsorshipHackathonDetailRepository;
    private final SponsorshipHackathonDetailMapper sponsorshipHackathonDetailMapper;

    @Override
    public SponsorshipHackathonDetailDTO create(SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO) {
        log.info("Creating new sponsorship hackathon detail");
        SponsorshipHackathonDetail sponsorshipHackathonDetail =
                sponsorshipHackathonDetailMapper.toEntity(sponsorshipHackathonDetailDTO);
        sponsorshipHackathonDetail = sponsorshipHackathonDetailRepository.save(sponsorshipHackathonDetail);
        return sponsorshipHackathonDetailMapper.toDto(sponsorshipHackathonDetail);
    }

    @Override
    public SponsorshipHackathonDetailDTO update(Long id, SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO) {
        log.info("Updating sponsorship hackathon detail with id: {}", id);
        SponsorshipHackathonDetail sponsorshipHackathonDetail = sponsorshipHackathonDetailRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship hackathon detail not found"));
        sponsorshipHackathonDetailMapper.updateEntityFromDto(sponsorshipHackathonDetailDTO, sponsorshipHackathonDetail);
        sponsorshipHackathonDetail = sponsorshipHackathonDetailRepository.save(sponsorshipHackathonDetail);
        return sponsorshipHackathonDetailMapper.toDto(sponsorshipHackathonDetail);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting sponsorship hackathon detail with id: {}", id);
        if (!sponsorshipHackathonDetailRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sponsorship hackathon detail not found");
        }
        sponsorshipHackathonDetailRepository.deleteById(id);
    }

    @Override
    public List<SponsorshipHackathonDetailDTO> getAll() {
        log.info("Fetching all sponsorship hackathon details");
        return sponsorshipHackathonDetailRepository.findAll().stream()
                .map(sponsorshipHackathonDetailMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SponsorshipHackathonDetailDTO getById(Long id) {
        log.info("Fetching sponsorship hackathon detail with id: {}", id);
        SponsorshipHackathonDetail sponsorshipHackathonDetail = sponsorshipHackathonDetailRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship hackathon detail not found"));
        return sponsorshipHackathonDetailMapper.toDto(sponsorshipHackathonDetail);
    }
}
