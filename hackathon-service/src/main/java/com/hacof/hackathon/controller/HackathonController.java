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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.hackathon.constant.StatusCode;
import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.dto.HackathonResultDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.service.HackathonResultService;
import com.hacof.hackathon.service.HackathonService;
import com.hacof.hackathon.specification.HackathonSpecification;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/hackathons")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class HackathonController {
    /**
     * This class is responsible for handling all the endpoints related to hackathons and hackathon results.
     */
    // --- ENDPOINTS FOR HACKATHONS ---
    HackathonService hackathonService;

    HackathonResultService hackathonResultService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<HackathonDTO>>> getByAllCriteria(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) Integer minTeamSize,
            @RequestParam(required = false) Integer maxTeamSize) {

        Specification<Hackathon> spec = Specification.where(HackathonSpecification.hasId(id)
                .and(HackathonSpecification.hasTitle(title))
                .and(HackathonSpecification.hasDescription(description))
                .and(HackathonSpecification.hasStatus(status))
                .and(HackathonSpecification.hasCategory(category))
                .and(HackathonSpecification.hasMaximumTeamMembers(maxTeamSize))
                .and(HackathonSpecification.hasMinimumTeamMembers(minTeamSize))
                .and(HackathonSpecification.hasStartDate(startDate))
                .and(HackathonSpecification.hasEndDate(endDate)));
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
    @PreAuthorize("hasAuthority('CREATE_HACKATHON')")
    public ResponseEntity<CommonResponse<HackathonDTO>> createHackathon(
            @Valid @RequestBody CommonRequest<HackathonDTO> request) {
        if (request.getData() == null) {
            throw new InvalidInputException("Hackathon data cannot be null");
        }
        HackathonDTO createdHackathon = hackathonService.create(request.getData());
        CommonResponse<HackathonDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Hackathon created successfully"),
                createdHackathon);
        log.info("Hackathon created response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_HACKATHON')")
    public ResponseEntity<CommonResponse<HackathonDTO>> updateHackathon(
            @Valid @RequestBody CommonRequest<HackathonDTO> request) {
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_HACKATHON')")
    public ResponseEntity<CommonResponse<Void>> deleteHackathon(@PathVariable String id) {
        log.debug("Received request to delete hackathon with id: {}", id);
        if (id == null || id.isEmpty()) {
            throw new InvalidInputException("Hackathon ID cannot be null or empty");
        }
        hackathonService.deleteHackathon(Long.parseLong(id));
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Hackathon deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    // --- ENDPOINTS FOR HACKATHON RESULTS ---
    @PostMapping("/results")
    @PreAuthorize("hasAuthority('CREATE_HACKATHON_RESULT')")
    public ResponseEntity<CommonResponse<HackathonResultDTO>> createHackathonResult(
            @Valid @RequestBody CommonRequest<HackathonResultDTO> request) {
        log.debug("Creating hackathon result: {}", request.getData());
        HackathonResultDTO created = hackathonResultService.create(request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Hackathon result created successfully"),
                created));
    }

    @PutMapping("/results")
    @PreAuthorize("hasAuthority('UPDATE_HACKATHON_RESULT')")
    public ResponseEntity<CommonResponse<HackathonResultDTO>> updateHackathonResult(
            @Valid @RequestBody CommonRequest<HackathonResultDTO> request) {
        String id = request.getData().getId();
        HackathonResultDTO updated = hackathonResultService.update(id, request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Hackathon result updated successfully"),
                updated));
    }

    @DeleteMapping("/results/{id}")
    @PreAuthorize("hasAuthority('DELETE_HACKATHON_RESULT')")
    public ResponseEntity<CommonResponse<HackathonResultDTO>> deleteHackathonResult(@PathVariable String id) {
        hackathonResultService.delete(Long.parseLong(id));

        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Hackathon result deleted successfully"),
                null));
    }

    @PostMapping("/results/bulk-create")
    @PreAuthorize("hasAuthority('CREATE_BULK_HACKATHON_RESULT')")
    public ResponseEntity<CommonResponse<List<HackathonResultDTO>>> createBulkHackathonResults(
            @Valid @RequestBody CommonRequest<List<HackathonResultDTO>> request) {
        List<HackathonResultDTO> created = hackathonResultService.createBulk(request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Bulk hackathon results created successfully"),
                created));
    }

    @PutMapping("/results/bulk-update")
    @PreAuthorize("hasAuthority('UPDATE_BULK_HACKATHON_RESULT')")
    public ResponseEntity<CommonResponse<List<HackathonResultDTO>>> updateBulkHackathonResults(
            @Valid @RequestBody CommonRequest<List<HackathonResultDTO>> request) {
        List<HackathonResultDTO> created = hackathonResultService.updateBulk(request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Bulk hackathon results created successfully"),
                created));
    }

    @GetMapping("/results/filter-by-hackathonId")
    public ResponseEntity<CommonResponse<List<HackathonResultDTO>>> getAllByHackathonId(
            @RequestParam("hackathonId") String hackathonId) {
        List<HackathonResultDTO> results = hackathonResultService.getAllByHackathonId(hackathonId);
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Fetched hackathon results successfully"),
                results));
    }

    @GetMapping("/results")
    public ResponseEntity<CommonResponse<List<HackathonResultDTO>>> getAllHackathonResults() {
        List<HackathonResultDTO> results = hackathonResultService.getAll();
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Fetched all hackathon results successfully"),
                results));
    }

    @GetMapping("/results/{id}")
    public ResponseEntity<CommonResponse<HackathonResultDTO>> getHackathonResultById(@PathVariable Long id) {
        HackathonResultDTO result = hackathonResultService.getById(id);
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Fetched hackathon result successfully"),
                result));
    }
}
