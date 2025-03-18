package com.hacof.hackathon.controller;

import java.time.LocalDateTime;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.hackathon.dto.SponsorshipDTO;
import com.hacof.hackathon.service.SponsorshipService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/sponsorships")
@RequiredArgsConstructor
public class SponsorshipController {
    private final SponsorshipService sponsorshipService;

    @PostMapping("/create")
    public ResponseEntity<CommonResponse<SponsorshipDTO>> createSponsorship(
            @Valid @RequestBody CommonRequest<SponsorshipDTO> request) {
        SponsorshipDTO result = sponsorshipService.createSponsorship(
                request.getData().getHackathonId(), request.getData().getSponsorName());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Sponsorship created successfully"),
                result));
    }

    @PostMapping("/approve")
    public ResponseEntity<CommonResponse<SponsorshipDTO>> approveSponsorship(
            @Valid @RequestBody CommonRequest<Long> request) {
        SponsorshipDTO result = sponsorshipService.approveSponsorship(request.getData(), request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Sponsorship approved"),
                result));
    }

    @PostMapping("/reject")
    public ResponseEntity<CommonResponse<SponsorshipDTO>> rejectSponsorship(
            @Valid @RequestBody CommonRequest<Long> request) {
        SponsorshipDTO result = sponsorshipService.rejectSponsorship(request.getData(), request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Sponsorship rejected"),
                result));
    }
}
