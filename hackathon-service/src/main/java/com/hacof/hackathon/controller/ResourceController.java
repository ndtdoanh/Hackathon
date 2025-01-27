package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<CommonResponse<ResourceDTO>> allocateResource(
            @RequestBody CommonRequest<ResourceDTO> request) {
        ResourceDTO resourceDTO = resourceService.allocateResource(request.getData());
        CommonResponse<ResourceDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Resource allocated successfully"),
                resourceDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ResourceDTO>>> getAllResources() {
        List<ResourceDTO> resources = resourceService.getAllResources();
        CommonResponse<List<ResourceDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all resources successfully"),
                resources);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{resourceId}")
    public ResponseEntity<CommonResponse<ResourceDTO>> getResourceById(@PathVariable Long resourceId) {
        ResourceDTO resource = resourceService.getResourceById(resourceId);
        CommonResponse<ResourceDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched resource successfully"),
                resource);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{resourceId}")
    public ResponseEntity<CommonResponse<ResourceDTO>> updateResource(
            @PathVariable Long resourceId, @RequestBody CommonRequest<ResourceDTO> request) {
        ResourceDTO resourceDTO = resourceService.updateResource(resourceId, request.getData());
        CommonResponse<ResourceDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Resource updated successfully"),
                resourceDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{resourceId}")
    public ResponseEntity<CommonResponse<Void>> deleteResource(@PathVariable Long resourceId) {
        resourceService.deleteResource(resourceId);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Resource deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }
}
