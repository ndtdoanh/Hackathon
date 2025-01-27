package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.constant.StatusCode;
import com.hacof.hackathon.dto.CampusDTO;
import com.hacof.hackathon.service.CampusService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/campuses")
@RequiredArgsConstructor
@Slf4j
public class CampusController {
    private final CampusService campusService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<CampusDTO>>> getAllCampuses() {
        List<CampusDTO> campuses = campusService.getAllCampuses();
        CommonResponse<List<CampusDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Fetched all campuses successfully"),
                campuses);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<CampusDTO>> getCampusById(@PathVariable Long id) {
        CampusDTO campus = campusService.getCampusById(id);
        CommonResponse<CampusDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Fetched campus successfully"),
                campus);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<CampusDTO>> createCampus(@RequestBody CommonRequest<CampusDTO> request) {
        log.debug("Received request to create campus: {}", request);
        CampusDTO campusDTO = campusService.createCampus(request.getData());
        CommonResponse<CampusDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Campus created successfully"),
                campusDTO);
        log.debug("Created campus: {}", campusDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<CampusDTO>> updateCampus(
            @PathVariable Long id, @RequestBody CommonRequest<CampusDTO> request) {
        CampusDTO campusDTO = campusService.updateCampus(id, request.getData());
        CommonResponse<CampusDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Campus updated successfully"),
                campusDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteCampus(@PathVariable Long id) {
        campusService.deleteCampus(id);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Campus deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }
}
