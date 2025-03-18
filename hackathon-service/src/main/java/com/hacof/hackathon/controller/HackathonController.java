package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/hackathons")
@RequiredArgsConstructor
@Slf4j
public class HackathonController {
    final HackathonService hackathonService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<HackathonDTO>>> getByAllCriteria(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) int numberRound,
            @RequestParam(required = false) int maxTeams,
            @RequestParam(required = false) int minTeamSize,
            @RequestParam(required = false) int maxTeamSize,
            @RequestParam(required = false) String status) {

        Specification<Hackathon> spec = Specification.where(HackathonSpecification.hasId(id))
                .and(HackathonSpecification.hasName(name))
                .and(HackathonSpecification.hasDescription(description))
                .and(HackathonSpecification.hasNumberRound(numberRound))
                .and(HackathonSpecification.hasMaxTeamsGreaterThan(maxTeams))
                .and(HackathonSpecification.hasStatus(status));

        List<HackathonDTO> hackathons = hackathonService.getHackathons(spec);
        CommonResponse<List<HackathonDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Fetched hackathons successfully"),
                hackathons);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<HackathonDTO>> createHackathon(
            @RequestBody @Valid CommonRequest<HackathonDTO> request) {
        log.debug("Received request to create hackathon: {}", request);
        HackathonDTO createdHackathon = hackathonService.createHackathon(request.getData());
        CommonResponse<HackathonDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Hackathon created successfully"),
                createdHackathon);
        log.debug("Created hackathon: {}", createdHackathon);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<HackathonDTO>> updateHackathon(@RequestBody @Valid HackathonDTO hackathonDTO) {
        log.debug("Received request to update hackathon: {}", hackathonDTO);
        long id = hackathonDTO.getId();
        HackathonDTO updatedHackathon = hackathonService.updateHackathon(id, hackathonDTO);
        CommonResponse<HackathonDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Hackathon updated successfully"),
                updatedHackathon);
        log.debug("Updated hackathon: {}", updatedHackathon);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> deleteHackathon(@RequestBody HackathonDTO hackathonDTO) {
        long id = hackathonDTO.getId();
        log.debug("Received request to delete hackathon with id: {}", id);
        hackathonService.deleteHackathon(id);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Hackathon deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }
}
