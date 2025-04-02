package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.SponsorshipDTO;
import com.hacof.hackathon.entity.Sponsorship;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.SponsorshipMapper;
import com.hacof.hackathon.repository.SponsorshipRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.SponsorshipService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SponsorshipServiceImpl implements SponsorshipService {
    private final SponsorshipRepository sponsorshipRepository;
    private final UserRepository userRepository;
    private final SponsorshipMapper sponsorshipMapper;

    @Override
    public SponsorshipDTO create(SponsorshipDTO sponsorshipDTO) {
        Sponsorship sponsorship = sponsorshipMapper.toEntity(sponsorshipDTO);
        sponsorship = sponsorshipRepository.save(sponsorship);
        return sponsorshipMapper.toDto(sponsorship);
    }

    @Override
    public SponsorshipDTO update(String id, SponsorshipDTO sponsorshipDTO) {
        Sponsorship sponsorship = sponsorshipRepository
                .findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }

        String username = authentication.getName();
        User currentUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        User createdBy = sponsorship.getCreatedBy();

        sponsorship.setName(sponsorshipDTO.getName());
        sponsorship.setBrand(sponsorshipDTO.getBrand());
        sponsorship.setContent(sponsorshipDTO.getContent());
        sponsorship.setMoney(sponsorshipDTO.getMoney());
        sponsorship.setTimeFrom(sponsorshipDTO.getTimeFrom());
        sponsorship.setTimeTo(sponsorshipDTO.getTimeTo());
        sponsorship.setCreatedBy(createdBy);
        sponsorship.setLastModifiedBy(currentUser);
        sponsorship.setLastModifiedDate(sponsorshipDTO.getUpdatedAt());

        sponsorship = sponsorshipRepository.save(sponsorship);
        return sponsorshipMapper.toDto(sponsorship);
    }

    @Override
    public void delete(Long id) {
        if (!sponsorshipRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sponsorship not found");
        }
        sponsorshipRepository.deleteById(id);
    }

    @Override
    public List<SponsorshipDTO> getAll(Specification<Sponsorship> specification) {
        if (sponsorshipRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No sponsorships found");
        }
        return sponsorshipRepository.findAll(specification).stream()
                .map(sponsorshipMapper::toDto)
                .collect(Collectors.toList());
    }
}
