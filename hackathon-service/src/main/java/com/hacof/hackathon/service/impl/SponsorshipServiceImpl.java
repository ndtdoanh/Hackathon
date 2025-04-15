package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.SponsorshipDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.Sponsorship;
import com.hacof.hackathon.entity.SponsorshipHackathon;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.manual.SponsorshipMapperManual;
import com.hacof.hackathon.repository.SponsorshipRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.SponsorshipService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true)
public class SponsorshipServiceImpl implements SponsorshipService {
    SponsorshipRepository sponsorshipRepository;
    UserRepository userRepository;

    @Override
    public SponsorshipDTO create(SponsorshipDTO sponsorshipDTO) {
        if (sponsorshipDTO.getTimeFrom() == null || sponsorshipDTO.getTimeTo() == null) {
            throw new InvalidInputException("Time from and time to must be provided");
        }
        if (sponsorshipDTO.getTimeFrom().isAfter(sponsorshipDTO.getTimeTo())) {
            throw new InvalidInputException("Time from must be before time to");
        }
        if (sponsorshipDTO.getTimeFrom().isBefore(LocalDateTime.now())) {
            throw new InvalidInputException("Time from must be in the future");
        }
        if (sponsorshipDTO.getTimeTo().isBefore(LocalDateTime.now())) {
            throw new InvalidInputException("Time to must be in the future");
        }

        Sponsorship sponsorship = SponsorshipMapperManual.toEntity(sponsorshipDTO);
        sponsorship = sponsorshipRepository.save(sponsorship);
        return SponsorshipMapperManual.toDto(sponsorship);
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

        // Update sponsorship hackathons
        final Sponsorship finalSponsorship = sponsorship;
        Set<SponsorshipHackathon> sponsorshipHackathons = sponsorshipDTO.getSponsorshipHackathons() != null
                ? sponsorshipDTO.getSponsorshipHackathons().stream()
                        .map(sponsorshipHackathonDTO -> {
                            SponsorshipHackathon sponsorshipHackathon = new SponsorshipHackathon();
                            if (sponsorshipHackathonDTO.getId() != null) {
                                sponsorshipHackathon.setId(Long.parseLong(sponsorshipHackathonDTO.getId()));
                            }
                            sponsorshipHackathon.setSponsorship(finalSponsorship);
                            Hackathon hackathon = new Hackathon();
                            hackathon.setId(Long.parseLong(sponsorshipHackathonDTO.getHackathonId()));
                            sponsorshipHackathon.setHackathon(hackathon);
                            sponsorshipHackathon.setTotalMoney(sponsorshipHackathonDTO.getTotalMoney());
                            return sponsorshipHackathon;
                        })
                        .collect(Collectors.toSet())
                : new HashSet<>();
        sponsorship.setSponsorshipHackathons(sponsorshipHackathons);
        sponsorship = sponsorshipRepository.save(sponsorship);
        return SponsorshipMapperManual.toDto(sponsorship);
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
        return sponsorshipRepository.findAll(specification).stream()
                .map(SponsorshipMapperManual::toDto)
                .collect(Collectors.toList());
    }
}
