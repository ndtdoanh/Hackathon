package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.CategoryStatus;
import com.hacof.hackathon.constant.OrganizationStatus;
import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.HackathonMapper;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.HackathonService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@FieldDefaults(makeFinal = true)
public class HackathonServiceImpl implements HackathonService {
    HackathonRepository hackathonRepository;
    HackathonMapper hackathonMapper;
    UserRepository userRepository;

    @Override
    public HackathonDTO create(HackathonDTO hackathonDTO) {
        validateEnumValues(hackathonDTO);
        Hackathon hackathon = hackathonMapper.toEntity(hackathonDTO);
        hackathon.setDocumentation(hackathonMapper.mapToFileUrlList(hackathonDTO.getDocumentation()));
        return hackathonMapper.toDto(hackathonRepository.save(hackathon));
    }

    @Override
    @Transactional
    public HackathonDTO update(String id, HackathonDTO hackathonDTO) {
        Hackathon existingHackathon = hackathonRepository
                .findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found with id: " + id));

        validateEnumValues(hackathonDTO);
        // get current user's information from SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }

        String username = authentication.getName();
        User currentUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        // hackathonMapper.updateEntityFromDto(hackathonDTO, existingHackathon);
        existingHackathon.setTitle(hackathonDTO.getTitle());
        existingHackathon.setSubTitle(hackathonDTO.getSubTitle());
        existingHackathon.setBannerImageUrl(hackathonDTO.getBannerImageUrl());
        existingHackathon.setEnrollStartDate(hackathonDTO.getEnrollStartDate());
        existingHackathon.setEnrollEndDate(hackathonDTO.getEnrollEndDate());
        existingHackathon.setStartDate(hackathonDTO.getEnrollStartDate());
        existingHackathon.setEndDate(hackathonDTO.getEnrollEndDate());
        existingHackathon.setDescription(hackathonDTO.getDescription());
        existingHackathon.setInformation(hackathonDTO.getInformation());
        existingHackathon.setMinTeamSize(hackathonDTO.getMinimumTeamMembers());
        existingHackathon.setMaxTeamSize(hackathonDTO.getMaximumTeamMembers());
        existingHackathon.setContact(hackathonDTO.getContact());
        existingHackathon.setCategory(CategoryStatus.valueOf(hackathonDTO.getCategory()));
        existingHackathon.setOrganization(OrganizationStatus.valueOf(hackathonDTO.getOrganization()));
        existingHackathon.setMaxTeams(hackathonDTO.getEnrollmentCount());
        existingHackathon.setStatus(Status.valueOf(hackathonDTO.getStatus()));
        existingHackathon.setDocumentation(hackathonMapper.mapToFileUrlList(hackathonDTO.getDocumentation()));

        // still not change createdBy
        User createdBy = existingHackathon.getCreatedBy();
        existingHackathon.setLastModifiedBy(currentUser);
        existingHackathon.setLastModifiedDate(LocalDateTime.now());
        existingHackathon.setCreatedBy(createdBy);
        return hackathonMapper.toDto(hackathonRepository.save(existingHackathon));
    }

    @Override
    public void deleteHackathon(Long id) {
        log.info("Deleting hackathon with id: {}", id);

        // Check if exists
        if (!hackathonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hackathon not found with id: " + id);
        }

        hackathonRepository.deleteById(id);
    }

    @Override
    public List<HackathonDTO> getHackathons(Specification<Hackathon> spec) {
        log.debug("Searching hackathons with specification");
        List<Hackathon> hackathons = hackathonRepository.findAll(spec);

        log.debug("Found {} hackathons matching the criteria", hackathons.size());
        return hackathons.stream().map(hackathonMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public boolean existsByTitle(String title) {
        return hackathonRepository.existsByTitle(title);
    }

    @Override
    public boolean existsByTitleAndIdNot(String title, Long id) {
        return hackathonRepository.existsByTitleAndIdNot(title, id);
    }

    private void validateEnumValues(HackathonDTO hackathonDTO) {
        try {
            CategoryStatus.valueOf(hackathonDTO.getCategory());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid category value: " + hackathonDTO.getCategory());
        }

        try {
            Status.valueOf(hackathonDTO.getStatus());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid status value: " + hackathonDTO.getStatus());
        }

        try {
            OrganizationStatus.valueOf(hackathonDTO.getOrganization());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid organization value: " + hackathonDTO.getOrganization());
        }
    }
}
