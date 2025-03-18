package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.service.RoundService;
import com.hacof.hackathon.specification.RoundSpecification;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/rounds")
@RequiredArgsConstructor
public class RoundController {
    final RoundService roundService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<RoundDTO>>> getAllRounds(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Integer maxTeam,
            @RequestParam(required = false) Boolean isVideoRound) {
        Specification<Round> spec = Specification.where(null);
        spec = spec.and(id != null ? RoundSpecification.hasId(id) : null);
        spec = spec.and(name != null ? RoundSpecification.hasName(name) : null);
        spec = spec.and(description != null ? RoundSpecification.hasDescription(description) : null);
        spec = spec.and(startDate != null ? RoundSpecification.hasStartDate(startDate) : null);
        spec = spec.and(endDate != null ? RoundSpecification.hasEndDate(endDate) : null);
        spec = spec.and(maxTeam != null ? RoundSpecification.hasMaxTeam(maxTeam) : null);
        spec = spec.and(isVideoRound != null ? RoundSpecification.hasIsVideoRound(isVideoRound) : null);

        List<RoundDTO> rounds = roundService.getRounds(spec);
        CommonResponse<List<RoundDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all rounds successfully"),
                rounds);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    // @PreAuthorize("hasAuthority('CREATE_ROUND')")
    public ResponseEntity<CommonResponse<RoundDTO>> createRound(@Valid @RequestBody CommonRequest<RoundDTO> request) {
        RoundDTO roundDTO = roundService.createRound(request.getData());
        CommonResponse<RoundDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Round created successfully"),
                roundDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    // @PreAuthorize("hasAuthority('UPDATE_ROUND')")
    public ResponseEntity<CommonResponse<RoundDTO>> updateRound(@Valid @RequestBody CommonRequest<RoundDTO> request) {
        RoundDTO roundDTO = roundService.updateRound(request.getData().getId(), request.getData());
        CommonResponse<RoundDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Round updated successfully"),
                roundDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    // @PreAuthorize("hasAuthority('DELETE_ROUND')")
    public ResponseEntity<CommonResponse<Void>> deleteRound(@RequestBody CommonRequest<RoundDTO> request) {
        roundService.deleteRound(request.getData().getId());
        CommonResponse<Void> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Round deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }
}
