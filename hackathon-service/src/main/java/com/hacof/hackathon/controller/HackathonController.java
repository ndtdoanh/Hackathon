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
import com.hacof.hackathon.service.HackathonService;
import com.hacof.hackathon.specification.HackathonSpecification;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/hackathons")
@RequiredArgsConstructor
@Slf4j
public class HackathonController {
    final HackathonService hackathonService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<HackathonDTO>>> getAllHackathons() {
        log.debug("Received request to fetch all hackathons");
        List<HackathonDTO> hackathons = hackathonService.getHackathons();
        CommonResponse<List<HackathonDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Fetched hackathons successfully"),
                hackathons);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hello")
    // @PreAuthorize("hasAuthority('GET_HACKATHON')")
    public ResponseEntity<CommonResponse<List<HackathonDTO>>> getByAllCriteria(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String subTitle,
            @RequestParam(required = false) String bannerImageUrl,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    LocalDateTime enrollStartDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    LocalDateTime enrollEndDate,
            @RequestParam(required = false) Integer enrollmentCount,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) String information,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String contact,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String organization,
            @RequestParam(required = false) String enrollmentStatus,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer minimumTeamMembers,
            @RequestParam(required = false) Integer maximumTeamMembers) {
        log.debug("Received request to fetch hackathons by all criteria");
        Specification<Hackathon> spec = HackathonSpecification.filter(
                id,
                title,
                subTitle,
                bannerImageUrl,
                enrollStartDate,
                enrollEndDate,
                enrollmentCount,
                startDate,
                endDate,
                information,
                description,
                contact,
                category,
                organization,
                enrollmentStatus,
                status,
                minimumTeamMembers,
                maximumTeamMembers);

        List<HackathonDTO> hackathons = hackathonService.getHackathons(spec);
        CommonResponse<List<HackathonDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Fetched hackathons successfully"),
                hackathons);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_HACKATHON')")
    public ResponseEntity<CommonResponse<HackathonDTO>> createHackathon(
            @RequestBody @Valid CommonRequest<HackathonDTO> request) {
        log.debug("Received request to create hackathon: {}", request);
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
            @RequestBody @Valid CommonRequest<HackathonDTO> request) {
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
}
