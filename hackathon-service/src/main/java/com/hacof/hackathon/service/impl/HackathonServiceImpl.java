package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.HackathonMapper;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.UserRepository;
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
    final UserRepository userRepository;

    @Override
    public HackathonDTO create(HackathonDTO hackathonDTO) {
        Hackathon hackathon = hackathonMapper.toEntity(hackathonDTO);
        return hackathonMapper.toDto(hackathonRepository.save(hackathon));
    }

    @Override
    @Transactional
    public HackathonDTO update(String id, HackathonDTO hackathonDTO) {
        Hackathon existingHackathon = hackathonRepository
                .findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found with id: " + id));

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
        existingHackathon.setDescription(hackathonDTO.getDescription());
        existingHackathon.setInformation(hackathonDTO.getInformation());
        existingHackathon.setStartDate(hackathonDTO.getStartDate());
        existingHackathon.setEndDate(hackathonDTO.getEndDate());
        existingHackathon.setMinTeamSize(hackathonDTO.getMinimumTeamMembers());
        existingHackathon.setMaxTeamSize(hackathonDTO.getMaximumTeamMembers());
        existingHackathon.setContact(hackathonDTO.getContact());
        existingHackathon.setCategory(hackathonDTO.getCategory());
        existingHackathon.setStatus(Status.valueOf(hackathonDTO.getStatus()));

        existingHackathon.setLastModifiedBy(currentUser);
        existingHackathon.setLastModifiedDate(LocalDateTime.now());
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
        log.debug("Searching hackathons with specification");
        List<Hackathon> hackathons = hackathonRepository.findAll(spec);

        if (hackathons.isEmpty()) {
            throw new ResourceNotFoundException("No hackathons found matching the criteria");
        }

        log.debug("Found {} hackathons matching the criteria", hackathons.size());
        return hackathons.stream().map(hackathonMapper::toDto).collect(Collectors.toList());
    }
}
