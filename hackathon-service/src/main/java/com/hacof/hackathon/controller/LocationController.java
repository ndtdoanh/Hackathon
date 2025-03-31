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
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/locations")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true)
public class LocationController {
    LocationService locationService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<LocationDTO>>> getLocations(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address) {
        Specification<Location> spec = Specification.where(null);
        if (id != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("id"), id));
        }
        if (name != null) {
            spec = spec.and((root, query, cb) -> cb.like(root.get("name"), "%" + name + "%"));
        }
        if (address != null) {
            spec = spec.and((root, query, cb) -> cb.like(root.get("address"), "%" + address + "%"));
        }

        List<LocationDTO> locationDTOs = locationService.getLocations(spec);

        CommonResponse<List<LocationDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Locations retrieved successfully"),
                locationDTOs);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<LocationDTO>> createLocation(
            @Valid @RequestBody CommonRequest<LocationDTO> request) {
        LocationDTO locationDTO = locationService.create(request.getData());
        CommonResponse<LocationDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Location created successfully"),
                locationDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<LocationDTO>> updateLocation(
            @Valid @RequestBody CommonRequest<LocationDTO> request) {
        LocationDTO locationDTO =
                locationService.update(Long.parseLong(request.getData().getId()), request.getData());
        CommonResponse<LocationDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Location updated successfully"),
                locationDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<LocationDTO>> deleteLocation(@RequestBody CommonRequest<LocationDTO> request) {
        locationService.delete(Long.parseLong(request.getData().getId()));
        CommonResponse<LocationDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Location deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }
}
