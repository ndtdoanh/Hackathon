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
import com.hacof.hackathon.mapper.manual.SponsorshipHackathonMapperManual;
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

    @Override
    public SponsorshipHackathonDTO create(SponsorshipHackathonDTO sponsorshipHackathonDTO) {
        log.info("Creating new sponsorship hackathon");

        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(sponsorshipHackathonDTO.getHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));

        Sponsorship sponsorship = sponsorshipRepository
                .findById(Long.parseLong(sponsorshipHackathonDTO.getSponsorshipId()))
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship not found"));

        validateRemainingMoney(sponsorship.getId(), sponsorshipHackathonDTO.getTotalMoney(), null);

        SponsorshipHackathon sponsorshipHackathon = SponsorshipHackathonMapperManual.toEntity(sponsorshipHackathonDTO);
        sponsorshipHackathon.setHackathon(hackathon);
        sponsorshipHackathon.setSponsorship(sponsorship);

        sponsorshipHackathon = sponsorshipHackathonRepository.save(sponsorshipHackathon);
        return SponsorshipHackathonMapperManual.toDto(sponsorshipHackathon);
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

        validateRemainingMoney(
                sponsorship.getId(), sponsorshipHackathonDTO.getTotalMoney(), sponsorshipHackathon.getId());

        sponsorshipHackathon.setTotalMoney(sponsorshipHackathonDTO.getTotalMoney());

        sponsorshipHackathon.setHackathon(hackathon);
        sponsorshipHackathon.setSponsorship(sponsorship);

        sponsorshipHackathon = sponsorshipHackathonRepository.save(sponsorshipHackathon);

        return SponsorshipHackathonMapperManual.toDto(sponsorshipHackathon);
    }

    private void validateRemainingMoney(Long sponsorshipId, double incomingMoney, Long excludeSponsorshipHackathonId) {
        double usedMoney = sponsorshipHackathonRepository.findAllBySponsorshipId(sponsorshipId).stream()
                .filter(sh -> excludeSponsorshipHackathonId == null || sh.getId() != excludeSponsorshipHackathonId)
                .mapToDouble(SponsorshipHackathon::getTotalMoney)
                .sum();

        double availableMoney = sponsorshipRepository
                .findById(sponsorshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship not found"))
                .getMoney();

        if (usedMoney + incomingMoney > availableMoney) {
            throw new ResourceNotFoundException("Total money exceeds available sponsorship budget");
        }
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
        return sponsorshipHackathonRepository.findAll().stream()
                .map(SponsorshipHackathonMapperManual::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SponsorshipHackathonDTO getById(String id) {
        return sponsorshipHackathonRepository
                .findById(Long.parseLong(id))
                .map(SponsorshipHackathonMapperManual::toDto)
                .orElse(null);
    }

    @Override
    public List<SponsorshipHackathonDTO> getAllByHackathonId(String hackathonId) {
        return sponsorshipHackathonRepository.findAllByHackathonId(Long.parseLong(hackathonId)).stream()
                .map(SponsorshipHackathonMapperManual::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SponsorshipHackathonDTO> getAllBySponsorshipId(String sponsorshipId) {
        return sponsorshipHackathonRepository.findAllBySponsorshipId(Long.parseLong(sponsorshipId)).stream()
                .map(SponsorshipHackathonMapperManual::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SponsorshipHackathonDTO getByHackathonAndSponsorshipId(String hackathonId, String sponsorshipId) {
        SponsorshipHackathon sponsorshipHackathon = sponsorshipHackathonRepository.findByHackathonIdAndSponsorshipId(
                Long.parseLong(hackathonId), Long.parseLong(sponsorshipId));

        if (sponsorshipHackathon == null) {
            throw new ResourceNotFoundException("SponsorshipHackathon not found");
        }

        return SponsorshipHackathonMapperManual.toDto(sponsorshipHackathon);
    }
}
