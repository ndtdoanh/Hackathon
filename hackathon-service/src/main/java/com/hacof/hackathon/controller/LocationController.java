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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/locations")
@RequiredArgsConstructor
@Slf4j
public class LocationController {
    private final LocationService locationService;

    @Operation(summary = "Get locations with filters")
    @GetMapping
    public ResponseEntity<CommonResponse<List<LocationDTO>>> getLocations(
            @Parameter(description = "Location ID") @RequestParam(required = false) Long id,
            @Parameter(description = "Location name") @RequestParam(required = false) String name,
            @Parameter(description = "Location address") @RequestParam(required = false) String address,
            @Parameter(description = "Minimum latitude") @RequestParam(required = false) Double minLat,
            @Parameter(description = "Maximum latitude") @RequestParam(required = false) Double maxLat,
            @Parameter(description = "Minimum longitude") @RequestParam(required = false) Double minLng,
            @Parameter(description = "Maximum longitude") @RequestParam(required = false) Double maxLng) {

        log.info("REST request to get Locations with filters: id={}, name={}, address={}", id, name, address);

        Specification<Location> spec = Specification.where(null);

        if (id != null) {
            spec = spec.and(LocationSpecification.hasId(id));
        }
        if (name != null) {
            spec = spec.and(LocationSpecification.hasName(name));
        }
        if (address != null) {
            spec = spec.and(LocationSpecification.hasAddress(address));
        }
        if (minLat != null && maxLat != null) {
            spec = spec.and(LocationSpecification.hasLatitudeBetween(minLat, maxLat));
        }
        if (minLng != null && maxLng != null) {
            spec = spec.and(LocationSpecification.hasLongitudeBetween(minLng, maxLng));
        }

        List<LocationDTO> result = locationService.getLocations(spec);
        CommonResponse<List<LocationDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Locations fetched successfully"),
                result);
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
