package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.hacof.hackathon.exception.InvalidInputException;
import jakarta.validation.Valid;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.service.RoundLocationService;
import com.hacof.hackathon.service.RoundService;
import com.hacof.hackathon.specification.RoundSpecification;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/rounds")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class RoundController {
    // This class used to manage the rounds of a hackathon and round locations
    RoundService roundService;
    RoundLocationService roundLocationService;

    // ----------- ROUND ENDPOINTS -----------
    @PostMapping
    public ResponseEntity<CommonResponse<RoundDTO>> createRound(@Valid @RequestBody CommonRequest<RoundDTO> request) {
        RoundDTO roundDTO = roundService.create(request.getData());
        CommonResponse<RoundDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Round created successfully"),
                roundDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<RoundDTO>> updateRound(@Valid @RequestBody CommonRequest<RoundDTO> request) {
        if (request.getData() == null) {
            throw new InvalidInputException("Round data cannot be null");
        }

        RoundDTO roundDTO = roundService.update(request.getData().getId(), request.getData());
        CommonResponse<RoundDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Round updated successfully"),
                roundDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<RoundDTO>> deleteRound(@PathVariable String id) {
        roundService.delete(Long.parseLong(id));
        CommonResponse<RoundDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Round deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<RoundDTO>>> getRounds(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String roundNumber,
            @RequestParam(required = false) String roundTitle,
            @RequestParam(required = false) String hackathonId,
            @RequestParam(required = false) String createdBy) {

        Specification<Round> spec = Specification.where(RoundSpecification.hasId(id))
                .and(RoundSpecification.hasRoundNumber(roundNumber))
                .and(RoundSpecification.hasRoundTitle(roundTitle))
                .and(RoundSpecification.hasHackathonId(hackathonId))
                .and(RoundSpecification.hasCreatedBy(createdBy));

        List<RoundDTO> roundDTOs = roundService.getRounds(spec);
        CommonResponse<List<RoundDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Rounds retrieved successfully"),
                roundDTOs);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/locations")
    public ResponseEntity<CommonResponse<RoundLocationDTO>> createRoundLocation(
            @RequestBody @Valid CommonRequest<RoundLocationDTO> request) {
        RoundLocationDTO roundLocationDTO = roundLocationService.create(request.getData());
        CommonResponse<RoundLocationDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Round Location created successfully"),
                roundLocationDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/locations")
    public ResponseEntity<CommonResponse<RoundLocationDTO>> updateRoundLocation(
            @RequestBody @Valid CommonRequest<RoundLocationDTO> request) {
        RoundLocationDTO roundLocationDTO =
                roundLocationService.update(Long.parseLong(request.getData().getId()), request.getData());
        CommonResponse<RoundLocationDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Round Location updated successfully"),
                roundLocationDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/locations/{id}")
    public ResponseEntity<CommonResponse<RoundLocationDTO>> deleteRoundLocation(@PathVariable String id) {
        roundLocationService.delete(Long.parseLong(id));
        CommonResponse<RoundLocationDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Round Location deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/locations")
    public ResponseEntity<CommonResponse<List<RoundLocationDTO>>> getAllRoundLocations() {
        List<RoundLocationDTO> roundLocations = roundLocationService.getAll();
        CommonResponse<List<RoundLocationDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all Round Locations successfully"),
                roundLocations);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/locations/{id}")
    public ResponseEntity<CommonResponse<RoundLocationDTO>> getRoundLocationById(@PathVariable Long id) {
        RoundLocationDTO roundLocationDTO = roundLocationService.getById(id);
        CommonResponse<RoundLocationDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched Round Location successfully"),
                roundLocationDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter-by-hackathon")
    public ResponseEntity<CommonResponse<List<RoundDTO>>> getAllByHackathonId(@RequestParam String hackathonId) {
        List<RoundDTO> rounds = roundService.getAllByHackathonId(hackathonId);
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched rounds successfully"),
                rounds));
    }
}
