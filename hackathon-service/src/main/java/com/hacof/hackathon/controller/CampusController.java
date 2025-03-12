package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.constant.StatusCode;
import com.hacof.hackathon.dto.CampusDTO;
import com.hacof.hackathon.entity.Campus;
import com.hacof.hackathon.service.CampusService;
import com.hacof.hackathon.specification.CampusSpecification;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/campuses")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CampusController {
    final CampusService campusService;

    // getByAllCriteria
    @GetMapping()
    public ResponseEntity<CommonResponse<List<CampusDTO>>> searchCampuses(
            @RequestParam(required = false) Long Id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String lastModifiedBy) {

        Specification<Campus> spec = Specification.where(CampusSpecification.hasId(Id))
                .and(CampusSpecification.hasName(name))
                .and(CampusSpecification.hasLocation(location))
                .and(CampusSpecification.createdBy(createdBy))
                .and(CampusSpecification.lastModifiedBy(lastModifiedBy));

        List<CampusDTO> campuses = campusService.searchCampuses(spec);
        CommonResponse<List<CampusDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Fetched campus successfully"),
                campuses);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<CampusDTO>> createCampus(
            @Valid @RequestBody CommonRequest<CampusDTO> request) {
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

    @PutMapping
    public ResponseEntity<CommonResponse<CampusDTO>> updateCampus(
            @Valid @RequestBody CommonRequest<CampusDTO> request) {
        Long id = request.getData().getId();
        CampusDTO campusDTO = campusService.updateCampus(id, request.getData());
        CommonResponse<CampusDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Campus updated successfully"),
                campusDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> deleteCampus(@RequestBody CommonRequest<CampusDTO> request) {
        Long id = request.getData().getId();
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
