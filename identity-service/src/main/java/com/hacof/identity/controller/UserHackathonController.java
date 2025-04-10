package com.hacof.identity.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.hacof.identity.dto.ApiRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.request.UserHackathonBulkRequestDTO;
import com.hacof.identity.dto.request.UserHackathonRequestDTO;
import com.hacof.identity.dto.response.UserHackathonResponseDTO;
import com.hacof.identity.service.UserHackathonService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("api/v1/user-hackathons")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserHackathonController {
    UserHackathonService userHackathonService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_USER_HACKATHON')")
    public ResponseEntity<ApiResponse<UserHackathonResponseDTO>> createUserHackathon(
            @RequestBody @Valid ApiRequest<UserHackathonRequestDTO> request) {
        UserHackathonResponseDTO response = userHackathonService.createUserHackathon(request.getData());
        ApiResponse<UserHackathonResponseDTO> apiResponse = ApiResponse.<UserHackathonResponseDTO>builder()
                .requestId(request.getRequestId())
                .requestDateTime(request.getRequestDateTime())
                .channel(request.getChannel())
                .data(response)
                .message("UserHackathon created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping
    public ApiResponse<List<UserHackathonResponseDTO>> getUserHackathons() {
        return ApiResponse.<List<UserHackathonResponseDTO>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userHackathonService.getUserHackathons())
                .message("Get all UserHackathons")
                .build();
    }

    @GetMapping("/{Id}")
    public ApiResponse<UserHackathonResponseDTO> getUserHackathon(@PathVariable("Id") Long id) {
        return ApiResponse.<UserHackathonResponseDTO>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userHackathonService.getUserHackathon(id))
                .message("Get UserHackathon by Id")
                .build();
    }

    @GetMapping("/hackathon/{hackathonId}")
    public ApiResponse<List<UserHackathonResponseDTO>> getUserHackathonsByHackathonId(
            @PathVariable("hackathonId") Long hackathonId) {
        List<UserHackathonResponseDTO> response = userHackathonService.getUserHackathonsByHackathonId(hackathonId);

        return ApiResponse.<List<UserHackathonResponseDTO>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(response)
                .message("Get all UserHackathons by Hackathon ID")
                .build();
    }

    @GetMapping("/hackathon/{hackathonId}/roles")
    public ApiResponse<List<UserHackathonResponseDTO>> getUserHackathonsByHackathonIdAndRoles(
            @PathVariable Long hackathonId, @RequestParam List<String> roles) {

        List<UserHackathonResponseDTO> userHackathons =
                userHackathonService.getUserHackathonsByHackathonIdAndRoles(hackathonId, roles);

        return ApiResponse.<List<UserHackathonResponseDTO>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(userHackathons)
                .message("Get UserHackathons by Hackathon ID and Roles")
                .build();
    }

    @DeleteMapping("/{Id}")
    @PreAuthorize("hasAuthority('DELETE_USER_HACKATHON')")
    public ApiResponse<Void> deleteUserHackathon(@PathVariable("Id") Long id) {
        userHackathonService.deleteUserHackathon(id);
        return ApiResponse.<Void>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .message("UserHackathon has been deleted")
                .build();
    }

    @PostMapping("/bulk")
    @PreAuthorize("hasAuthority('CREATE_USER_HACKATHON')")
    public ResponseEntity<ApiResponse<List<UserHackathonResponseDTO>>> createBulkUserHackathon(
            @RequestBody @Valid ApiRequest<UserHackathonBulkRequestDTO> bulkRequest) {

        List<UserHackathonResponseDTO> responses = userHackathonService.createBulkUserHackathon(bulkRequest.getData());

        ApiResponse<List<UserHackathonResponseDTO>> apiResponse = ApiResponse.<List<UserHackathonResponseDTO>>builder()
                .requestId(bulkRequest.getRequestId())
                .requestDateTime(bulkRequest.getRequestDateTime())
                .channel(bulkRequest.getChannel())
                .data(responses)
                .message("Bulk create UserHackathons successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
