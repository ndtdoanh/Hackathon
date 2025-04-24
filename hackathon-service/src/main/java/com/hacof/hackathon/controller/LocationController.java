package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.hackathon.constant.StatusCode;
import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.entity.Location;
import com.hacof.hackathon.service.LocationService;
import com.hacof.hackathon.specification.LocationSpecification;
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
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String lastModifiedBy) {

        Specification<Location> spec = Specification.where(LocationSpecification.hasId(id))
                .and(LocationSpecification.hasName(name))
                .and(LocationSpecification.hasAddress(address))
                .and(LocationSpecification.hasLongitude(longitude))
                .and(LocationSpecification.hasCreatedBy(createdBy))
                .and(LocationSpecification.hasLastModifiedBy(lastModifiedBy))
                .and(LocationSpecification.hasLatitude(latitude));

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
    @PreAuthorize("hasAuthority('CREATE_LOCATION')")
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
    @PreAuthorize("hasAuthority('UPDATE_LOCATION')")
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_LOCATION')")
    public ResponseEntity<CommonResponse<LocationDTO>> deleteLocation(@PathVariable String id) {
        locationService.delete(Long.parseLong(id));
        CommonResponse<LocationDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Location deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }
}
