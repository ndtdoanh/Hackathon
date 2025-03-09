package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.service.HackathonService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/hackathons")
@RequiredArgsConstructor
public class HackathonController {
    private final HackathonService hackathonService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<HackathonDTO>>> getAllHackathons() {
        List<HackathonDTO> hackathons = hackathonService.getAllHackathons();
        CommonResponse<List<HackathonDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all hackathons successfully"),
                hackathons);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{hackathonId}")
    public ResponseEntity<CommonResponse<HackathonDTO>> getHackathonById(@PathVariable Long hackathonId) {
        HackathonDTO hackathon = hackathonService.getHackathonById(hackathonId);
        CommonResponse<HackathonDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched hackathon successfully"),
                hackathon);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<HackathonDTO>> createHackathon(
            @RequestBody CommonRequest<HackathonDTO> request) {
        HackathonDTO hackathonDTO = hackathonService.createHackathon(request.getData());
        CommonResponse<HackathonDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Hackathon created successfully"),
                hackathonDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{hackathonId}")
    public ResponseEntity<CommonResponse<HackathonDTO>> updateHackathon(
            @PathVariable Long hackathonId, @RequestBody CommonRequest<HackathonDTO> request) {
        HackathonDTO hackathonDTO = hackathonService.updateHackathon(hackathonId, request.getData());
        CommonResponse<HackathonDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Hackathon updated successfully"),
                hackathonDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{hackathonId}")
    public ResponseEntity<CommonResponse<Void>> deleteHackathon(@PathVariable Long hackathonId) {
        hackathonService.deleteHackathon(hackathonId);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Hackathon deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }
}
