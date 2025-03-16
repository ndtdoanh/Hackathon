package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.constant.StatusCode;
import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.service.HackathonService;
import com.hacof.hackathon.specification.HackathonSpecification;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/hackathons")
@RequiredArgsConstructor
public class HackathonController {
    private final HackathonService hackathonService;

    @GetMapping("/all")
    public ResponseEntity<CommonResponse<List<HackathonDTO>>> getByAllCriteria(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String bannerImageUrl,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) Integer numberRound,
            @RequestParam(required = false) Integer maxTeams,
            @RequestParam(required = false) Integer minTeamSize,
            @RequestParam(required = false) Integer maxTeamSize,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String lastModifiedBy) {
        Specification<Hackathon> spec = Specification.where(HackathonSpecification.idEquals(id))
                .and(HackathonSpecification.hasName(name))
                // .and(HackathonSpecification.nameContains(name))
                .and(HackathonSpecification.bannerImageUrlContains(bannerImageUrl))
                .and(HackathonSpecification.descriptionContains(description))
                // .and(HackathonSpecification.startDateGreaterThan(startDate))
                // .and(HackathonSpecification.endDateLessThan(endDate))
                .and(HackathonSpecification.numberRoundEquals(numberRound))
                .and(HackathonSpecification.maxTeamsEquals(maxTeams))
                .and(HackathonSpecification.minTeamSizeEquals(minTeamSize))
                .and(HackathonSpecification.maxTeamSizeEquals(maxTeamSize))
                .and(HackathonSpecification.statusEquals(status))
                .and(HackathonSpecification.createdByEquals(createdBy))
                .and(HackathonSpecification.lastModifiedByEquals(lastModifiedBy));
        List<HackathonDTO> hackathons = hackathonService.getAllHackathon(spec);
        CommonResponse<List<HackathonDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all hackathons successfully"),
                hackathons);
        return ResponseEntity.ok(response);
    }

    //    @GetMapping
    //    public ResponseEntity<CommonResponse<List<HackathonDTO>>> getByAllCriteria(
    //
    //    }

    @PostMapping
    public ResponseEntity<CommonResponse<HackathonDTO>> createHackathon(
            @Valid @RequestBody CommonRequest<HackathonDTO> request) {
        HackathonDTO hackathonDTO = hackathonService.createHackathon(request.getData());
        CommonResponse<HackathonDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Hackathon created successfully"),
                hackathonDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<CommonResponse<HackathonDTO>> updateHackathon(
            @Valid @RequestBody CommonRequest<HackathonDTO> request) {
        Long hackathonId = request.getData().getId();
        HackathonDTO hackathonDTO = hackathonService.updateHackathon(hackathonId, request.getData());
        CommonResponse<HackathonDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Hackathon updated successfully"),
                hackathonDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping()
    public ResponseEntity<CommonResponse<Void>> deleteHackathon(@RequestBody CommonRequest<HackathonDTO> request) {
        Long hackathonId = request.getData().getId();
        hackathonService.deleteHackathon(hackathonId);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Hackathon deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }
}
