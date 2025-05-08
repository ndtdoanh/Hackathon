package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.BoardUserRole;
import com.hacof.hackathon.constant.CategoryStatus;
import com.hacof.hackathon.constant.OrganizationStatus;
import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.Board;
import com.hacof.hackathon.entity.BoardUser;
import com.hacof.hackathon.entity.FileUrl;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.Schedule;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.entity.UserHackathon;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.HackathonMapper;
import com.hacof.hackathon.repository.BoardRepository;
import com.hacof.hackathon.repository.BoardUserRepository;
import com.hacof.hackathon.repository.FileUrlRepository;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.ScheduleRepository;
import com.hacof.hackathon.repository.TeamRequestRepository;
import com.hacof.hackathon.repository.UserHackathonRepository;
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
    FileUrlRepository fileUrlRepository;
    TeamRequestRepository teamRequestRepository;
    // update 23/4/25:
    ScheduleRepository scheduleRepository;
    BoardRepository boardRepository;
    BoardUserRepository boardUserRepository;
    UserHackathonRepository userHackathonRepository;

    @Override
    public HackathonDTO create(HackathonDTO hackathonDTO) {
        validateEnumValues(hackathonDTO);
        validateUniqueTitleForCreate(hackathonDTO.getTitle());

        Hackathon hackathon = hackathonMapper.toEntity(hackathonDTO);
        hackathon.setDocumentation(null);
        Authentication authentication = getAuthenticatedUser();
        String username = authentication.getName();
        User currentUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        hackathon.setCreatedBy(currentUser);
        hackathon.setCreatedDate(LocalDateTime.now());

        hackathon = hackathonRepository.save(hackathon);

        UserHackathon userHackathon = UserHackathon.builder()
                .user(currentUser)
                .hackathon(hackathon)
                .role("ORGANIZER")
                .build();
        userHackathonRepository.save(userHackathon);

        if (hackathonDTO.getDocumentation() != null
                && !hackathonDTO.getDocumentation().isEmpty()) {
            List<FileUrl> fileUrls =
                    fileUrlRepository.findAllByFileUrlInAndHackathonIsNull(hackathonDTO.getDocumentation());
            for (FileUrl file : fileUrls) {
                file.setHackathon(hackathon);
            }
            fileUrlRepository.saveAll(fileUrls);
            hackathon.setDocumentation(fileUrls);
        }

        hackathon = hackathonRepository.save(hackathon);

        // Create Schedule
        Schedule schedule = Schedule.builder()
                .hackathon(hackathon)
                .name("Default Schedule for " + hackathon.getTitle())
                .description("This is the default schedule for the hackathon.")
                .build();
        scheduleRepository.save(schedule);

        // Create Board
        Board board = Board.builder()
                .name("Default Board for " + hackathon.getTitle())
                .description("This is the default board for the hackathon.")
                .owner(currentUser)
                .hackathon(hackathon)
                .build();
        boardRepository.save(board);

        // Create BoardUser
        BoardUser boardUser = BoardUser.builder()
                .board(board)
                .user(currentUser)
                .role(BoardUserRole.ADMIN)
                .isDeleted(false)
                .build();
        boardUserRepository.save(boardUser);

        return hackathonMapper.toDto(hackathon);
    }

    @Override
    @Transactional
    public HackathonDTO update(String id, HackathonDTO hackathonDTO) {
        Hackathon existingHackathon = hackathonRepository
                .findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found with id: " + id));

        if (hackathonDTO.getTitle() == null || hackathonDTO.getTitle().trim().isEmpty()) {
            throw new InvalidInputException("Title cannot be empty");
        }

        validateUniqueTitleForUpdate(id, hackathonDTO.getTitle());
        validateEnumValues(hackathonDTO);

        Authentication authentication = getAuthenticatedUser();
        String username = authentication.getName();
        User currentUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        existingHackathon.setTitle(hackathonDTO.getTitle());
        existingHackathon.setSubTitle(hackathonDTO.getSubTitle());
        existingHackathon.setBannerImageUrl(hackathonDTO.getBannerImageUrl());
        existingHackathon.setEnrollStartDate(hackathonDTO.getEnrollStartDate());
        existingHackathon.setEnrollEndDate(hackathonDTO.getEnrollEndDate());
        existingHackathon.setStartDate(hackathonDTO.getStartDate());
        existingHackathon.setEndDate(hackathonDTO.getEndDate());
        existingHackathon.setDescription(hackathonDTO.getDescription());
        existingHackathon.setInformation(hackathonDTO.getInformation());
        existingHackathon.setMinTeamSize(hackathonDTO.getMinimumTeamMembers());
        existingHackathon.setMaxTeamSize(hackathonDTO.getMaximumTeamMembers());
        existingHackathon.setContact(hackathonDTO.getContact());
        existingHackathon.setCategory(CategoryStatus.valueOf(hackathonDTO.getCategory()));
        existingHackathon.setOrganization(OrganizationStatus.valueOf(hackathonDTO.getOrganization()));
        existingHackathon.setMaxTeams(hackathonDTO.getEnrollmentCount());
        existingHackathon.setStatus(Status.valueOf(hackathonDTO.getStatus()));
        /* about update documentation:
         * Step 1: Set null current documentations
         * Step 2: Set new documentations
         */
        // Step 1: Set null current documentations
        List<FileUrl> currentDocs = fileUrlRepository.findAllByHackathon(existingHackathon);
        for (FileUrl doc : currentDocs) {
            doc.setHackathon(null);
        }
        fileUrlRepository.saveAll(currentDocs);

        // Step 2: Set new documentations
        List<FileUrl> fileUrls =
                fileUrlRepository.findAllByFileUrlInAndHackathonIsNull(hackathonDTO.getDocumentation());

        for (FileUrl file : fileUrls) {
            file.setHackathon(existingHackathon); // reassign for hackathon
        }
        fileUrlRepository.saveAll(fileUrls);
        existingHackathon.setDocumentation(fileUrls);

        // still not change createdBy
        User createdBy = existingHackathon.getCreatedBy();
        existingHackathon.setLastModifiedBy(currentUser);
        existingHackathon.setLastModifiedDate(LocalDateTime.now());
        existingHackathon.setCreatedBy(createdBy);
        return hackathonMapper.toDto(hackathonRepository.save(existingHackathon));
    }

    @Override
    public void deleteHackathon(Long id) {
        //        // check Authority
        //        Authentication authentication = getAuthenticatedUser();
        //        if(!hasRole(authentication, "ADMIN") && !hasRole(authentication, "ORGANIZER")) {
        //            throw new InvalidInputException("You do not have permission to perform this action.");
        //        }

        // Check if exists
        if (!hackathonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hackathon not found with id: " + id);
        }

        // Check for dependent records
        if (teamRequestRepository.existsByHackathonId(id)) {
            throw new DataIntegrityViolationException(
                    "Cannot delete hackathon with id: " + id + " because it has dependent team requests.");
        }

        try {
            hackathonRepository.deleteById(id);
            log.info("Hackathon with id: {} deleted successfully.", id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                    "Failed to delete hackathon with id: " + id + ". Ensure all dependent records are removed first.",
                    e);
        }
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

    private void validateEnumValues(HackathonDTO hackathonDTO) {
        try {
            CategoryStatus.valueOf(hackathonDTO.getCategory());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(
                    "Invalid category value (CODING/EXTERNAL/INTERNAL/DESIGN/OTHERS): " + hackathonDTO.getCategory());
        }
        try {
            Status.valueOf(hackathonDTO.getStatus());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(
                    "Invalid status value (DRAFT/OPEN/ON_GOING/CLOSED): " + hackathonDTO.getStatus());
        }
        try {
            OrganizationStatus.valueOf(hackathonDTO.getOrganization());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid organization value (FPU/NASA/IAI HACKATHON/CE HACKATHON/OTHERS): "
                    + hackathonDTO.getOrganization());
        }
    }

    private void validateUniqueTitleForCreate(String title) {
        if (existsByTitle(title)) {
            throw new InvalidInputException("Hackathon with title '" + title + "' already exists.");
        }
    }

    private void validateUniqueTitleForUpdate(String id, String title) {
        boolean exists = hackathonRepository.existsByTitleIgnoreCaseAndIdNot(title, Long.parseLong(id));
        log.debug("Checking if title '{}' exists for another hackathon: {}", title, exists);
        if (exists) {
            throw new InvalidInputException("Hackathon with title '" + title + "' already exists.");
        }
    }

    private Authentication getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }
        return authentication;
    }
}
