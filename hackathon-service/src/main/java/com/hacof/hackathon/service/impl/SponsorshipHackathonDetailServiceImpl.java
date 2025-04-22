package com.hacof.hackathon.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hacof.hackathon.dto.SponsorshipHackathonDetailRequestDTO;
import com.hacof.hackathon.dto.SponsorshipHackathonDetailResponseDTO;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.SponsorshipDetailStatus;
import com.hacof.hackathon.dto.FileUrlResponse;
import com.hacof.hackathon.dto.SponsorshipHackathonDetailDTO;
import com.hacof.hackathon.entity.FileUrl;
import com.hacof.hackathon.entity.SponsorshipHackathon;
import com.hacof.hackathon.entity.SponsorshipHackathonDetail;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.FileUrlMapper;
import com.hacof.hackathon.mapper.manual.SponsorshipHackathonDetailMapperManual;
import com.hacof.hackathon.repository.FileUrlRepository;
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
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class SponsorshipHackathonDetailServiceImpl implements SponsorshipHackathonDetailService {
    SponsorshipHackathonDetailRepository sponsorshipHackathonDetailRepository;
    SponsorshipHackathonRepository sponsorshipHackathonRepository;
    FileUrlRepository fileUrlRepository;
    FileUrlMapper fileUrlMapper;

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

    //    @Override
    //    public SponsorshipHackathonDetailDTO create(SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO) {
    //        log.info("Creating new sponsorship hackathon detail");
    //
    //        SponsorshipHackathon sponsorshipHackathon = sponsorshipHackathonRepository
    //                .findById(Long.parseLong(sponsorshipHackathonDetailDTO.getSponsorshipHackathonId()))
    //                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship Hackathon not found"));
    //
    //        SponsorshipHackathonDetail sponsorshipHackathonDetail =
    //                SponsorshipHackathonDetailMapperManual.toEntity(sponsorshipHackathonDetailDTO);
    //        sponsorshipHackathonDetail.setSponsorshipHackathon(sponsorshipHackathon);
    //
    //        sponsorshipHackathonDetail = sponsorshipHackathonDetailRepository.save(sponsorshipHackathonDetail);
    //        return SponsorshipHackathonDetailMapperManual.toDto(sponsorshipHackathonDetail);
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
    //        SponsorshipHackathonDetailMapperManual.updateEntityFromDto(
    //                sponsorshipHackathonDetailDTO, sponsorshipHackathonDetail);
    //        sponsorshipHackathonDetail.setSponsorshipHackathon(sponsorshipHackathon);
    //
    //        sponsorshipHackathonDetail = sponsorshipHackathonDetailRepository.save(sponsorshipHackathonDetail);
    //        return SponsorshipHackathonDetailMapperManual.toDto(sponsorshipHackathonDetail);
    //    }

    @Override
    public SponsorshipHackathonDetailResponseDTO createWithFiles(SponsorshipHackathonDetailRequestDTO dto) {
        log.info("Creating new sponsorship hackathon detail");

        SponsorshipHackathon sponsorshipHackathon = sponsorshipHackathonRepository
                .findById(Long.parseLong(dto.getSponsorshipHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship Hackathon not found"));

        SponsorshipHackathonDetail entity = SponsorshipHackathonDetailMapperManual.toEntity(dto);
        entity.setSponsorshipHackathon(sponsorshipHackathon);

        final SponsorshipHackathonDetail finalEntity = entity;

        if (dto.getFileUrls() != null && !dto.getFileUrls().isEmpty()) {
            List<FileUrl> fileUrls = fileUrlRepository.findAllByFileUrlInAndSponsorshipHackathonDetailIsNull(dto.getFileUrls());
            fileUrls.forEach(f -> f.setSponsorshipHackathonDetail(finalEntity));
            fileUrlRepository.saveAll(fileUrls);
        }

        entity = sponsorshipHackathonDetailRepository.save(entity);
        return SponsorshipHackathonDetailMapperManual.toDto(entity);

    }

    @Override
    public SponsorshipHackathonDetailResponseDTO updateInfo(Long id, SponsorshipHackathonDetailRequestDTO dto) {
        log.info("Updating sponsorship hackathon detail info with id: {}", id);

        SponsorshipHackathonDetail entity = sponsorshipHackathonDetailRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship Hackathon Detail not found"));

        SponsorshipHackathon sponsorshipHackathon = sponsorshipHackathonRepository
                .findById(Long.parseLong(dto.getSponsorshipHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship Hackathon not found"));

        SponsorshipHackathonDetailMapperManual.updateEntityFromDto(dto, entity);
        entity.setSponsorshipHackathon(sponsorshipHackathon);

        entity = sponsorshipHackathonDetailRepository.save(entity);
        return SponsorshipHackathonDetailMapperManual.toDto(entity);
    }

    @Override
    public SponsorshipHackathonDetailResponseDTO updateFiles(Long id, List<String> fileUrls) {
        log.info("Updating sponsorship hackathon detail files for id: {}", id);

        SponsorshipHackathonDetail sponsorshipDetail = sponsorshipHackathonDetailRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship Hackathon Detail not found"));

        if (fileUrls != null && !fileUrls.isEmpty()) {
            List<FileUrl> newFileUrls = fileUrlRepository
                    .findAllByFileUrlInAndSponsorshipHackathonDetailIsNull(fileUrls);

            newFileUrls.forEach(f -> f.setSponsorshipHackathonDetail(sponsorshipDetail));
            fileUrlRepository.saveAll(newFileUrls);
        }

        SponsorshipHackathonDetail updatedDetail = sponsorshipHackathonDetailRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship Hackathon Detail not found after update"));

        return SponsorshipHackathonDetailMapperManual.toDto(updatedDetail);
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
    public List<SponsorshipHackathonDetailResponseDTO> getAll() {
        log.info("Fetching all sponsorship hackathon details");
        return sponsorshipHackathonDetailRepository.findAll().stream()
                .map(SponsorshipHackathonDetailMapperManual::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SponsorshipHackathonDetailResponseDTO getById(Long id) {
        return sponsorshipHackathonDetailRepository.findById(id)
                .map(SponsorshipHackathonDetailMapperManual::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship Hackathon Detail not found"));
    }

    @Override
    public List<SponsorshipHackathonDetailResponseDTO> getAllBySponsorshipHackathonId(String sponsorshipHackathonId) {
        return sponsorshipHackathonDetailRepository
                .findAllBySponsorshipHackathonId(Long.parseLong(sponsorshipHackathonId))
                .stream()
                .map(SponsorshipHackathonDetailMapperManual::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FileUrlResponse> getFileUrlsBySponsorshipHackathonDetailId(Long sponsorshipHackathonDetailId) {
        SponsorshipHackathonDetail scheduleEvent = sponsorshipHackathonDetailRepository
                .findById(sponsorshipHackathonDetailId)
                .orElseThrow(() -> new IllegalArgumentException("ScheduleEvent not found!"));

        List<FileUrl> fileUrls = scheduleEvent.getFileUrls();

        return fileUrlMapper.toResponseList(
                fileUrls != null ? fileUrls : Collections.emptyList()
        );
    }
}
