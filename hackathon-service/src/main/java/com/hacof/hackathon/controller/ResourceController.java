package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.constant.StatusCode;
import com.hacof.hackathon.dto.ResourceDTO;
import com.hacof.hackathon.service.ResourceService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/resources")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;

    @GetMapping
    @PreAuthorize("hasAuthority('GET_RESOURCES')")
    public ResponseEntity<CommonResponse<List<ResourceDTO>>> getAllResources() {
        List<ResourceDTO> resources = resourceService.getAllResources();
        CommonResponse<List<ResourceDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Fetched all resources successfully"),
                resources);
        return ResponseEntity.ok(response);
    }

    //    @GetMapping
    //    public ResponseEntity<CommonResponse<ResourceDTO>> getResourceById(
    //            @RequestBody CommonRequest<ResourceDTO> request) {
    //        Long resourceId = request.getData().getId();
    //        ResourceDTO resource = resourceService.getResourceById(resourceId);
    //        CommonResponse<ResourceDTO> response = new CommonResponse<>(
    //                UUID.randomUUID().toString(),
    //                LocalDateTime.now(),
    //                "HACOF",
    //                new CommonResponse.Result("0000", "Fetched resource successfully"),
    //                resource);
    //        return ResponseEntity.ok(response);
    //    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_RESOURCE')")
    public ResponseEntity<CommonResponse<ResourceDTO>> createResource(
            @Valid @RequestBody CommonRequest<ResourceDTO> request) {
        ResourceDTO resourceDTO = resourceService.createResource(request.getData());
        CommonResponse<ResourceDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Resource created successfully"),
                resourceDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_RESOURCE')")
    public ResponseEntity<CommonResponse<ResourceDTO>> updateResource(
            @Valid @RequestBody CommonRequest<ResourceDTO> request) {
        Long resourceId = request.getData().getId();
        ResourceDTO resourceDTO = resourceService.updateResource(resourceId, request.getData());
        CommonResponse<ResourceDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Resource updated successfully"),
                resourceDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('DELETE_RESOURCE')")
    public ResponseEntity<CommonResponse<Void>> deleteResource(@Valid @RequestBody CommonRequest<ResourceDTO> request) {
        Long resourceId = request.getData().getId();
        resourceService.deleteResource(resourceId);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Resource deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/round")
    @PreAuthorize("hasAuthority('GET_RESOURCES_BY_ROUND')")
    public ResponseEntity<CommonResponse<List<ResourceDTO>>> getResourcesByRoundId(
            @Valid @RequestBody CommonRequest<ResourceDTO> request) {
        Long roundId = request.getData().getCompetitionRoundId();
        List<ResourceDTO> resources = resourceService.getResourcesByRoundId(roundId);
        CommonResponse<List<ResourceDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Fetched resources for round successfully"),
                resources);
        return ResponseEntity.ok(response);
    }
}
