package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.service.RoundLocationService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/round-locations")
@RequiredArgsConstructor
public class RoundLocationController {
    private final RoundLocationService roundLocationService;

    @PostMapping
    public ResponseEntity<CommonResponse<RoundLocationDTO>> createRoundLocation(
            @RequestBody CommonRequest<RoundLocationDTO> request) {
        RoundLocationDTO roundLocationDTO = roundLocationService.create(request.getData());
        CommonResponse<RoundLocationDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "RoundLocation created successfully"),
                roundLocationDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<RoundLocationDTO>> updateRoundLocation(
            @RequestBody CommonRequest<RoundLocationDTO> request) {
        RoundLocationDTO roundLocationDTO =
                roundLocationService.update(Long.parseLong(request.getData().getId()), request.getData());
        CommonResponse<RoundLocationDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "RoundLocation updated successfully"),
                roundLocationDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<RoundLocationDTO>> deleteRoundLocation(
            @RequestBody CommonRequest<RoundLocationDTO> request) {
        roundLocationService.delete(Long.parseLong(request.getData().getId()));
        CommonResponse<RoundLocationDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "RoundLocation deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<RoundLocationDTO>>> getAllRoundLocations() {
        List<RoundLocationDTO> roundLocations = roundLocationService.getAll();
        CommonResponse<List<RoundLocationDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all round locations successfully"),
                roundLocations);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<RoundLocationDTO>> getRoundLocationById(@PathVariable Long id) {
        RoundLocationDTO roundLocation = roundLocationService.getById(id);
        CommonResponse<RoundLocationDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched round location successfully"),
                roundLocation);
        return ResponseEntity.ok(response);
    }
}
