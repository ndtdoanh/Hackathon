package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.SponsorshipHackathonDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.Sponsorship;
import com.hacof.hackathon.entity.SponsorshipHackathon;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.SponsorshipHackathonMapper;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.SponsorshipHackathonRepository;
import com.hacof.hackathon.repository.SponsorshipRepository;
import com.hacof.hackathon.service.SponsorshipHackathonService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@FieldDefaults(makeFinal = true)
public class SponsorshipHackathonServiceImpl implements SponsorshipHackathonService {
    SponsorshipHackathonRepository sponsorshipHackathonRepository;
    SponsorshipRepository sponsorshipRepository;
    HackathonRepository hackathonRepository;
    SponsorshipHackathonMapper sponsorshipHackathonMapper;

    @Override
    public SponsorshipHackathonDTO create(SponsorshipHackathonDTO sponsorshipHackathonDTO) {
        log.info("Creating new sponsorship hackathon");

        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(sponsorshipHackathonDTO.getHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));

        Sponsorship sponsorship = sponsorshipRepository
                .findById(Long.parseLong(sponsorshipHackathonDTO.getSponsorshipId()))
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship not found"));

        SponsorshipHackathon sponsorshipHackathon = sponsorshipHackathonMapper.toEntity(sponsorshipHackathonDTO);
        sponsorshipHackathon.setHackathon(hackathon);
        sponsorshipHackathon.setSponsorship(sponsorship);

        sponsorshipHackathon = sponsorshipHackathonRepository.save(sponsorshipHackathon);
        return sponsorshipHackathonMapper.toDto(sponsorshipHackathon);
    }

    @Override
    public SponsorshipHackathonDTO update(String id, SponsorshipHackathonDTO sponsorshipHackathonDTO) {
        log.info("Updating sponsorship hackathon with id: {}", id);

        SponsorshipHackathon sponsorshipHackathon = sponsorshipHackathonRepository
                .findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship hackathon not found"));

        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(sponsorshipHackathonDTO.getHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));

        Sponsorship sponsorship = sponsorshipRepository
                .findById(Long.parseLong(sponsorshipHackathonDTO.getSponsorshipId()))
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship not found"));

        sponsorshipHackathonMapper.updateEntityFromDto(sponsorshipHackathonDTO, sponsorshipHackathon);
        sponsorshipHackathon.setHackathon(hackathon);
        sponsorshipHackathon.setSponsorship(sponsorship);

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
        //        if (sponsorshipHackathonRepository.findAll().isEmpty()) {
        //            throw new ResourceNotFoundException("No sponsorship hackathon found");
        //        }
        return sponsorshipHackathonRepository.findAll().stream()
                .map(sponsorshipHackathonMapper::toDto)
                .collect(Collectors.toList());
    }
}
