package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                .and(HackathonSpecification.hasMinimumTeamMembers(minTeamSize)));

        List<HackathonDTO> hackathons = hackathonService.getHackathons(spec);

        CommonResponse<List<HackathonDTO>> response = new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
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
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Hackathon created successfully"),
                createdHackathon);
        log.debug("Created hackathon: {}", createdHackathon);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<HackathonDTO>> updateHackathon(
            @Valid @RequestBody CommonRequest<HackathonDTO> request) {
        // validate unique title
        validateUniqueTitleForUpdate(
                request.getData().getId(), request.getData().getTitle());

        HackathonDTO updatedHackathon =
                hackathonService.update(request.getData().getId(), request.getData());
        CommonResponse<HackathonDTO> response = new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Hackathon updated successfully"),
                updatedHackathon);
        log.debug("Updated hackathon: {}", updatedHackathon);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> deleteHackathon(@RequestBody HackathonDTO request) {
        Long id = Long.parseLong((request.getId()));
        log.debug("Received request to delete hackathon with id: {}", id);
        hackathonService.deleteHackathon(id);
        CommonResponse<Void> response = new CommonResponse<>(
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Hackathon deleted successfully"), null);
        return ResponseEntity.ok(response);
    }

    // --- ENDPOINTS FOR HACKATHON RESULTS ---
    @PostMapping("/results")
    public ResponseEntity<CommonResponse<HackathonResultDTO>> createHackathonResult(
            @Valid @RequestBody CommonRequest<HackathonResultDTO> request) {
        log.debug("Creating hackathon result: {}", request.getData());
        HackathonResultDTO created = hackathonResultService.create(request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Hackathon result created successfully"), created));
    }

    @PutMapping("/results")
    public ResponseEntity<CommonResponse<HackathonResultDTO>> updateHackathonResult(
            @Valid @RequestBody CommonRequest<HackathonResultDTO> request) {
        String id = request.getData().getId();
        HackathonResultDTO updated = hackathonResultService.update(id, request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Hackathon result updated successfully"), updated));
    }

    @DeleteMapping("/results")
    public ResponseEntity<CommonResponse<HackathonResultDTO>> deleteHackathonResult(
            @RequestBody CommonRequest<HackathonResultDTO> request) {
        hackathonResultService.delete(request.getData().getId());

        return ResponseEntity.ok(new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Hackathon result deleted successfully"), null));
    }

    @PostMapping("/results/bulk-create")
    public ResponseEntity<CommonResponse<List<HackathonResultDTO>>> createBulkHackathonResults(
            @Valid @RequestBody CommonRequest<List<HackathonResultDTO>> request) {
        log.debug("Bulk creating hackathon results");
        List<HackathonResultDTO> created = hackathonResultService.createBulk(request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Bulk hackathon results created successfully"), created));
    }

    @PutMapping("/results/bulk-update")
    public ResponseEntity<CommonResponse<List<HackathonResultDTO>>> updateBulkHackathonResults(
            @Valid @RequestBody CommonRequest<List<HackathonResultDTO>> request) {
        log.debug("Bulk updating hackathon results");
        List<HackathonResultDTO> updated = hackathonResultService.updateBulk(request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Bulk hackathon results updated successfully"), updated));
    }

    @GetMapping("/results/{hackathonId}")
    public ResponseEntity<CommonResponse<List<HackathonResultDTO>>> getAllByHackathonId(
            @PathVariable String hackathonId) {
        List<HackathonResultDTO> results = hackathonResultService.getAllByHackathonId(hackathonId);
        return ResponseEntity.ok(new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Fetched hackathon results successfully"), results));
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
