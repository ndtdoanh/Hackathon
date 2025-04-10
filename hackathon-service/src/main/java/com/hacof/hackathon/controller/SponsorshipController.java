package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.SponsorshipDTO;
import com.hacof.hackathon.dto.SponsorshipHackathonDTO;
import com.hacof.hackathon.dto.SponsorshipHackathonDetailDTO;
import com.hacof.hackathon.entity.Sponsorship;
import com.hacof.hackathon.service.SponsorshipHackathonDetailService;
import com.hacof.hackathon.service.SponsorshipHackathonService;
import com.hacof.hackathon.service.SponsorshipService;
import com.hacof.hackathon.specification.SponsorshipSpecification;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/sponsorships")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class SponsorshipController {
    // this class manages 3 components: Sponsorship, SponsorshipHackathon, SponsorshipHackathonDetail
    SponsorshipService sponsorshipService;
    SponsorshipHackathonService sponsorshipHackathonService;
    SponsorshipHackathonDetailService sponsorshipHackathonDetailService;

    @PostMapping
    public ResponseEntity<CommonResponse<SponsorshipDTO>> createSponsorship(
            @RequestBody @Valid CommonRequest<SponsorshipDTO> request) {
        SponsorshipDTO sponsorshipDTO = sponsorshipService.create(request.getData());
        CommonResponse<SponsorshipDTO> response = new CommonResponse<>(
                                request.getRequestId(),
                                LocalDateTime.now(),
                                request.getChannel(),
                new CommonResponse.Result("0000", "Sponsorship created successfully"), sponsorshipDTO);
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
                new CommonResponse.Result("0000", "Sponsorship updated successfully"), sponsorshipDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<SponsorshipDTO>> deleteSponsorship(@PathVariable Long id) {

        sponsorshipService.delete(id);
        CommonResponse<SponsorshipDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Sponsorship deleted successfully"), null);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<SponsorshipDTO>>> getSponsorships(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String money) {
        Specification<Sponsorship> spec = Specification.where(SponsorshipSpecification.hasId(id));
        //                .and(SponsorshipSpecification.hasName(name))
        //                .and(SponsorshipSpecification.hasBrand(brand))
        //                .and(SponsorshipSpecification.hasContent(content))
        //                .and(SponsorshipSpecification.hasMoney(money));

        List<SponsorshipDTO> sponsorships = sponsorshipService.getAll(spec);
        CommonResponse<List<SponsorshipDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all sponsorships successfully"), sponsorships);
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

    @DeleteMapping("/hackathons/{id}")
    public ResponseEntity<CommonResponse<SponsorshipHackathonDTO>> deleteSponsorshipHackathon(@PathVariable String id) {
        sponsorshipHackathonService.delete(Long.parseLong(id));
        CommonResponse<SponsorshipHackathonDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Sponsorship Hackathon deleted successfully"), null);
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

    @PostMapping("/details")
    public ResponseEntity<CommonResponse<SponsorshipHackathonDetailDTO>> createSponsorshipHackathonDetail(
            @RequestBody @Valid CommonRequest<SponsorshipHackathonDetailDTO> request) {
        SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO =
                sponsorshipHackathonDetailService.create(request.getData());
        CommonResponse<SponsorshipHackathonDetailDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Sponsorship Hackathon Detail created successfully"),
                sponsorshipHackathonDetailDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/details")
    public ResponseEntity<CommonResponse<SponsorshipHackathonDetailDTO>> updateSponsorshipHackathonDetail(
            @RequestBody @Valid CommonRequest<SponsorshipHackathonDetailDTO> request) {
        SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO = sponsorshipHackathonDetailService.update(
                Long.parseLong(request.getData().getId()), request.getData());
        CommonResponse<SponsorshipHackathonDetailDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Sponsorship Hackathon Detail updated successfully"),
                sponsorshipHackathonDetailDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/details/{id}")
    public ResponseEntity<CommonResponse<SponsorshipHackathonDetailDTO>> deleteSponsorshipHackathonDetail(
            @PathVariable String id) {
        sponsorshipHackathonDetailService.delete(Long.parseLong(id));
        CommonResponse<SponsorshipHackathonDetailDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Sponsorship Hackathon Detail deleted successfully"), null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details")
    public ResponseEntity<CommonResponse<List<SponsorshipHackathonDetailDTO>>> getAllSponsorshipHackathonDetails() {
        List<SponsorshipHackathonDetailDTO> sponsorshipHackathonDetails = sponsorshipHackathonDetailService.getAll();
        CommonResponse<List<SponsorshipHackathonDetailDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all Sponsorship Hackathon Details successfully"),
                sponsorshipHackathonDetails);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<CommonResponse<SponsorshipHackathonDetailDTO>> getSponsorshipHackathonDetailById(
            @PathVariable Long id) {
        SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO = sponsorshipHackathonDetailService.getById(id);
        CommonResponse<SponsorshipHackathonDetailDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched Sponsorship Hackathon Detail successfully"),
                sponsorshipHackathonDetailDTO);
        return ResponseEntity.ok(response);
    }
}
