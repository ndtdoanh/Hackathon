package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.constant.StatusCode;
import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.entity.Location;
import com.hacof.hackathon.service.LocationService;
import com.hacof.hackathon.specification.LocationSpecification;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/locations")
@RequiredArgsConstructor
@Slf4j
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<LocationDTO>>> getByAllCriteria(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String lastModifiedBy) {

        Specification<Location> spec = Specification.where(LocationSpecification.hasId(id))
                .and(LocationSpecification.hasName(name))
                .and(LocationSpecification.hasAddress(address))
                .and(LocationSpecification.hasLatitude(latitude))
                .and(LocationSpecification.hasLongitude(longitude))
                .and(LocationSpecification.createdBy(createdBy))
                .and(LocationSpecification.lastModifiedBy(lastModifiedBy));

        List<LocationDTO> locations = locationService.getAllLocations(spec);
        CommonResponse<List<LocationDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Fetched locations successfully"),
                locations);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<LocationDTO>> createLocation(
            @Valid @RequestBody CommonRequest<LocationDTO> request) {
        log.debug("Received request to create location: {}", request);
        LocationDTO locationDTO = locationService.createLocation(request.getData());
        CommonResponse<LocationDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Location created successfully"),
                locationDTO);
        log.debug("Created location: {}", locationDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<LocationDTO>> updateLocation(
            @Valid @RequestBody CommonRequest<LocationDTO> request) {
        Long id = request.getData().getId();
        LocationDTO locationDTO = locationService.updateLocation(id, request.getData());
        CommonResponse<LocationDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Location updated successfully"),
                locationDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> deleteLocation(@RequestBody CommonRequest<LocationDTO> request) {
        Long id = request.getData().getId();
        locationService.deleteLocation(id);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Location deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }
}
