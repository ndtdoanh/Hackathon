package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.SponsorshipDTO;
import com.hacof.hackathon.dto.SponsorshipHackathonDTO;
import com.hacof.hackathon.service.SponsorshipHackathonService;
import com.hacof.hackathon.service.SponsorshipService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/sponsorships")
@RequiredArgsConstructor
public class SponsorshipController {
    private final SponsorshipService sponsorshipService;
    private final SponsorshipHackathonService sponsorshipHackathonService;

    @PostMapping
    public ResponseEntity<CommonResponse<SponsorshipDTO>> createSponsorship(
            @RequestBody @Valid CommonRequest<SponsorshipDTO> request) {
        SponsorshipDTO sponsorshipDTO = sponsorshipService.create(request.getData());
        CommonResponse<SponsorshipDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Sponsorship created successfully"),
                sponsorshipDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<SponsorshipDTO>> updateSponsorship(
            @RequestBody @Valid CommonRequest<SponsorshipDTO> request) {
        SponsorshipDTO sponsorshipDTO =
                sponsorshipService.update(request.getData().getId(), request.getData());
        CommonResponse<SponsorshipDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Sponsorship updated successfully"),
                sponsorshipDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> deleteSponsorship(@RequestBody @Valid CommonRequest<Long> request) {
        sponsorshipService.delete(request.getData());
        CommonResponse<Void> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Sponsorship deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<SponsorshipDTO>>> getAllSponsorships() {
        List<SponsorshipDTO> sponsorships = sponsorshipService.getAll();
        CommonResponse<List<SponsorshipDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all sponsorships successfully"),
                sponsorships);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<SponsorshipDTO>> getSponsorshipById(@PathVariable Long id) {
        SponsorshipDTO sponsorship = sponsorshipService.getById(id);
        CommonResponse<SponsorshipDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched sponsorship successfully"),
                sponsorship);
        return ResponseEntity.ok(response);
    }

    // sponsorship hackathon service
    @PostMapping("/hackathons")
    public ResponseEntity<CommonResponse<SponsorshipHackathonDTO>> createSponsorshipHackathon(
            @RequestBody @Valid CommonRequest<SponsorshipHackathonDTO> request) {
        SponsorshipHackathonDTO sponsorshipHackathonDTO = sponsorshipHackathonService.create(request.getData());
        CommonResponse<SponsorshipHackathonDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Sponsorship Hackathon created successfully"),
                sponsorshipHackathonDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/hackathons")
    public ResponseEntity<CommonResponse<SponsorshipHackathonDTO>> updateSponsorshipHackathon(
            @RequestBody @Valid CommonRequest<SponsorshipHackathonDTO> request) {
        SponsorshipHackathonDTO sponsorshipHackathonDTO =
                sponsorshipHackathonService.update(request.getData().getId(), request.getData());
        CommonResponse<SponsorshipHackathonDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Sponsorship Hackathon updated successfully"),
                sponsorshipHackathonDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/hackathons")
    public ResponseEntity<CommonResponse<Void>> deleteSponsorshipHackathon(
            @RequestBody @Valid CommonRequest<Long> request) {
        sponsorshipHackathonService.delete(request.getData());
        CommonResponse<Void> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Sponsorship Hackathon deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hackathons")
    public ResponseEntity<CommonResponse<List<SponsorshipHackathonDTO>>> getAllSponsorshipHackathons() {
        List<SponsorshipHackathonDTO> sponsorshipHackathons = sponsorshipHackathonService.getAll();
        CommonResponse<List<SponsorshipHackathonDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all Sponsorship Hackathons successfully"),
                sponsorshipHackathons);
        return ResponseEntity.ok(response);
    }
}
