package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.service.LocationService;
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

    @PostMapping
    public ResponseEntity<CommonResponse<LocationDTO>> createLocation(@RequestBody CommonRequest<LocationDTO> request) {
        LocationDTO locationDTO = locationService.create(request.getData());
        CommonResponse<LocationDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Location created successfully"),
                locationDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<LocationDTO>> updateLocation(@RequestBody CommonRequest<LocationDTO> request) {
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

    //    @GetMapping
    //    public ResponseEntity<CommonResponse<List<LocationDTO>>> getAllLocations() {
    //        List<LocationDTO> locations = locationService.getAll();
    //        CommonResponse<List<LocationDTO>> response = new CommonResponse<>(
    //                UUID.randomUUID().toString(),
    //                LocalDateTime.now(),
    //                "HACOF",
    //                new CommonResponse.Result("0000", "Fetched all locations successfully"),
    //                locations);
    //        return ResponseEntity.ok(response);
    //    }
    //
    //    @GetMapping("/{id}")
    //    public ResponseEntity<CommonResponse<LocationDTO>> getLocationById(@PathVariable Long id) {
    //        LocationDTO location = locationService.getById(id);
    //        CommonResponse<LocationDTO> response = new CommonResponse<>(
    //                UUID.randomUUID().toString(),
    //                LocalDateTime.now(),
    //                "HACOF",
    //                new CommonResponse.Result("0000", "Fetched location successfully"),
    //                location);
    //        return ResponseEntity.ok(response);
    //    }
}
