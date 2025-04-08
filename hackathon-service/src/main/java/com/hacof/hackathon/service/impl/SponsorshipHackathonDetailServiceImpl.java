package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.SponsorshipHackathonDetailDTO;
import com.hacof.hackathon.entity.SponsorshipHackathon;
import com.hacof.hackathon.entity.SponsorshipHackathonDetail;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.manual.SponsorshipHackathonDetailMapperManual;
import com.hacof.hackathon.repository.SponsorshipHackathonDetailRepository;
import com.hacof.hackathon.repository.SponsorshipHackathonRepository;
import com.hacof.hackathon.service.SponsorshipHackathonDetailService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@FieldDefaults(makeFinal = true)
public class SponsorshipHackathonDetailServiceImpl implements SponsorshipHackathonDetailService {
    SponsorshipHackathonDetailRepository sponsorshipHackathonDetailRepository;
    SponsorshipHackathonRepository sponsorshipHackathonRepository;
    // SponsorshipHackathonDetailMapper sponsorshipHackathonDetailMapper;

    //    @Override
    //    public SponsorshipHackathonDetailDTO create(SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO) {
    //        log.info("Creating new sponsorship hackathon detail");
    //
    //        SponsorshipHackathon sponsorshipHackathon = sponsorshipHackathonRepository
    //                .findById(Long.parseLong(sponsorshipHackathonDetailDTO.getSponsorshipHackathonId()))
    //                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship Hackathon not found"));
    //
    //        SponsorshipHackathonDetail sponsorshipHackathonDetail =
    //                sponsorshipHackathonDetailMapper.toEntity(sponsorshipHackathonDetailDTO);
    //        sponsorshipHackathonDetail.setSponsorshipHackathon(sponsorshipHackathon);
    //
    //        sponsorshipHackathonDetail = sponsorshipHackathonDetailRepository.save(sponsorshipHackathonDetail);
    //        return sponsorshipHackathonDetailMapper.toDto(sponsorshipHackathonDetail);
    //    }
    //
    //    @Override
    //    public SponsorshipHackathonDetailDTO update(Long id, SponsorshipHackathonDetailDTO
    // sponsorshipHackathonDetailDTO) {
    //        log.info("Updating sponsorship hackathon detail with id: {}", id);
    //
    //        SponsorshipHackathonDetail sponsorshipHackathonDetail = sponsorshipHackathonDetailRepository
    //                .findById(id)
    //                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship hackathon detail not found"));
    //
    //        SponsorshipHackathon sponsorshipHackathon = sponsorshipHackathonRepository
    //                .findById(Long.parseLong(sponsorshipHackathonDetailDTO.getSponsorshipHackathonId()))
    //                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship Hackathon not found"));
    //
    //        sponsorshipHackathonDetailMapper.updateEntityFromDto(sponsorshipHackathonDetailDTO,
    // sponsorshipHackathonDetail);
    //        sponsorshipHackathonDetail.setSponsorshipHackathon(sponsorshipHackathon);
    //
    //        sponsorshipHackathonDetail = sponsorshipHackathonDetailRepository.save(sponsorshipHackathonDetail);
    //        return sponsorshipHackathonDetailMapper.toDto(sponsorshipHackathonDetail);
    //    }

    @Override
    public SponsorshipHackathonDetailDTO create(SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO) {
        log.info("Creating new sponsorship hackathon detail");

        SponsorshipHackathon sponsorshipHackathon = sponsorshipHackathonRepository
                .findById(Long.parseLong(sponsorshipHackathonDetailDTO.getSponsorshipHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship Hackathon not found"));

        SponsorshipHackathonDetail sponsorshipHackathonDetail =
                SponsorshipHackathonDetailMapperManual.toEntity(sponsorshipHackathonDetailDTO);
        sponsorshipHackathonDetail.setSponsorshipHackathon(sponsorshipHackathon);

        sponsorshipHackathonDetail = sponsorshipHackathonDetailRepository.save(sponsorshipHackathonDetail);
        return SponsorshipHackathonDetailMapperManual.toDto(sponsorshipHackathonDetail);
    }

    @Override
    public SponsorshipHackathonDetailDTO update(Long id, SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO) {
        log.info("Updating sponsorship hackathon detail with id: {}", id);

        SponsorshipHackathonDetail sponsorshipHackathonDetail = sponsorshipHackathonDetailRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship hackathon detail not found"));

        SponsorshipHackathon sponsorshipHackathon = sponsorshipHackathonRepository
                .findById(Long.parseLong(sponsorshipHackathonDetailDTO.getSponsorshipHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship Hackathon not found"));

        SponsorshipHackathonDetailMapperManual.updateEntityFromDto(
                sponsorshipHackathonDetailDTO, sponsorshipHackathonDetail);
        sponsorshipHackathonDetail.setSponsorshipHackathon(sponsorshipHackathon);

        sponsorshipHackathonDetail = sponsorshipHackathonDetailRepository.save(sponsorshipHackathonDetail);
        return SponsorshipHackathonDetailMapperManual.toDto(sponsorshipHackathonDetail);
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
        //        if (sponsorshipHackathonDetailRepository.findAll().isEmpty()) {
        //            throw new ResourceNotFoundException("No sponsorship hackathon details found");
        //        }
        return sponsorshipHackathonDetailRepository.findAll().stream()
                .map(SponsorshipHackathonDetailMapperManual::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SponsorshipHackathonDetailDTO getById(Long id) {
        Optional<SponsorshipHackathonDetail> sponsorshipHackathonDetailOptional =
                sponsorshipHackathonDetailRepository.findById(id);
        SponsorshipHackathonDetail sponsorshipHackathonDetail = sponsorshipHackathonDetailOptional.orElse(null);
        return SponsorshipHackathonDetailMapperManual.toDto(sponsorshipHackathonDetail);
    }
}
