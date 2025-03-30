package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.constant.StatusCode;
import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.service.HackathonService;
import com.hacof.hackathon.specification.HackathonSpecification;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/hackathons")
@RequiredArgsConstructor
@Slf4j
public class HackathonController {
    final HackathonService hackathonService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<HackathonDTO>>> getByAllCriteria(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) Integer minTeamSize,
            @RequestParam(required = false) Integer maxTeamSize) {
        log.debug(
                "Searching hackathons with criteria - keyword: {}, status: {}, category: {}",
                keyword,
                status,
                category);

        Specification<Hackathon> spec = Specification.where(null);

        if (StringUtils.isNotBlank(keyword)) {
            spec = spec.and(HackathonSpecification.searchByKeyword(keyword));
        }
        if (StringUtils.isNotBlank(status)) {
            spec = spec.and(HackathonSpecification.hasStatus(status));
        }
        if (StringUtils.isNotBlank(category)) {
            spec = spec.and(HackathonSpecification.byCategory(category));
        }
        if (startDate != null || endDate != null) {
            spec = spec.and(HackathonSpecification.datesBetween(startDate, endDate));
        }
        if (minTeamSize != null || maxTeamSize != null) {
            spec = spec.and(HackathonSpecification.teamSizeRange(minTeamSize, maxTeamSize));
        }

        List<HackathonDTO> hackathons = hackathonService.getHackathons(spec);

        CommonResponse<List<HackathonDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(
                        StatusCode.SUCCESS.getCode(), String.format("Found %d hackathons", hackathons.size())),
                hackathons);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<HackathonDTO>> createHackathon(
            @Valid @RequestBody CommonRequest<HackathonDTO> request) {
        log.debug("Received request to create hackathon: {}", request);
        // validate unique title
        validateUniqueTitleForCreate(request.getData().getTitle());

        HackathonDTO createdHackathon = hackathonService.create(request.getData());
        CommonResponse<HackathonDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Hackathon created successfully"),
                createdHackathon);
        log.debug("Created hackathon: {}", createdHackathon);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_HACKATHON')")
    public ResponseEntity<CommonResponse<HackathonDTO>> updateHackathon(
            @Valid @RequestBody CommonRequest<HackathonDTO> request) {
        // validate unique title
        validateUniqueTitleForUpdate(
                request.getData().getId(), request.getData().getTitle());

        HackathonDTO updatedHackathon =
                hackathonService.update(request.getData().getId(), request.getData());
        CommonResponse<HackathonDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Hackathon updated successfully"),
                updatedHackathon);
        log.debug("Updated hackathon: {}", updatedHackathon);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('DELETE_HACKATHON')")
    public ResponseEntity<CommonResponse<Void>> deleteHackathon(@RequestBody CommonResponse<HackathonDTO> request) {
        Long id = Long.parseLong((request.getData().getId()));
        log.debug("Received request to delete hackathon with id: {}", id);
        hackathonService.deleteHackathon(id);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Hackathon deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    private void validateUniqueTitleForCreate(String title) {
        if (hackathonService.existsByTitle(title)) {
            throw new InvalidInputException("Hackathon title already exists");
        }
    }

    private void validateUniqueTitleForUpdate(String id, String title) {
        if (hackathonService.existsByTitleAndIdNot(title, Long.parseLong(id))) {
            throw new InvalidInputException("Hackathon title already exists");
        }
    }
}
