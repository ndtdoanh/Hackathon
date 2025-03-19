package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.constant.StatusCode;
import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.dto.RoundLocationResponseDTO;
import com.hacof.hackathon.entity.RoundLocation;
import com.hacof.hackathon.service.RoundLocationService;
import com.hacof.hackathon.specification.RoundLocationSpecification;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/round-locations")
@RequiredArgsConstructor
@Slf4j
public class RoundLocationController {
    private final RoundLocationService roundLocationService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<RoundLocationResponseDTO>>> getByAllCriteria(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long roundId,
            @RequestParam(required = false) Long locationId,
            @RequestParam(required = false) String type) {

        Specification<RoundLocation> spec = Specification.where(RoundLocationSpecification.hasId(id))
                .and(RoundLocationSpecification.hasRoundId(roundId))
                .and(RoundLocationSpecification.hasLocationId(locationId))
                .and(RoundLocationSpecification.hasType(type));

        List<RoundLocationResponseDTO> roundLocations = roundLocationService.getAllRoundLocations(spec);
        CommonResponse<List<RoundLocationResponseDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Fetched round locations successfully"),
                roundLocations);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<RoundLocationResponseDTO>> createRoundLocation(
            @Valid @RequestBody CommonRequest<RoundLocationDTO> request) {
        log.debug("Received request to create round location: {}", request);
        RoundLocationResponseDTO roundLocationDTO = roundLocationService.createRoundLocation(request.getData());
        CommonResponse<RoundLocationResponseDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Round location created successfully"),
                roundLocationDTO);
        log.debug("Created round location: {}", roundLocationDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<RoundLocationResponseDTO>> updateRoundLocation(
            @Valid @RequestBody CommonRequest<RoundLocationDTO> request) {
        Long id = request.getData().getId();
        RoundLocationResponseDTO roundLocationDTO = roundLocationService.updateRoundLocation(id, request.getData());
        CommonResponse<RoundLocationResponseDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Round location updated successfully"),
                roundLocationDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> deleteRoundLocation(
            @RequestBody CommonRequest<RoundLocationDTO> request) {
        Long id = request.getData().getId();
        roundLocationService.deleteRoundLocation(id);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Round location deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }
}
