package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.service.RoundService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/rounds")
@RequiredArgsConstructor
public class RoundController {
    private final RoundService roundService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ROUND')")
    public ResponseEntity<CommonResponse<RoundDTO>> createRound(@RequestBody CommonRequest<RoundDTO> request) {
        RoundDTO roundDTO = roundService.create(request.getData());
        CommonResponse<RoundDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Round created successfully"),
                roundDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_ROUND')")
    public ResponseEntity<CommonResponse<RoundDTO>> updateRound(@RequestBody CommonRequest<RoundDTO> request) {
        RoundDTO roundDTO = roundService.update(request.getData().getId(), request.getData());
        CommonResponse<RoundDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Round updated successfully"),
                roundDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('DELETE_ROUND')")
    public ResponseEntity<CommonResponse<RoundDTO>> deleteRound(@RequestBody CommonRequest<RoundDTO> request) {
        roundService.delete(Long.parseLong(request.getData().getId()));
        CommonResponse<RoundDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Round deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<RoundDTO>>> getAllRounds() {
        List<RoundDTO> rounds = roundService.getAll();
        CommonResponse<List<RoundDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all rounds successfully"),
                rounds);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<RoundDTO>> getRoundById(@PathVariable Long id) {
        RoundDTO round = roundService.getById(id);
        CommonResponse<RoundDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched round successfully"),
                round);
        return ResponseEntity.ok(response);
    }
}
