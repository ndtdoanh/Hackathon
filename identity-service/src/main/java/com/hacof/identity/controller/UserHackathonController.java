package com.hacof.identity.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
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

import com.hacof.identity.dto.ApiResponse;
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
            @RequestBody @Valid UserHackathonRequestDTO request) {
        UserHackathonResponseDTO response = userHackathonService.createUserHackathon(request);
        ApiResponse<UserHackathonResponseDTO> apiResponse = ApiResponse.<UserHackathonResponseDTO>builder()
                .data(response)
                .message("UserHackathon created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping
    public ApiResponse<List<UserHackathonResponseDTO>> getUserHackathons() {
        return ApiResponse.<List<UserHackathonResponseDTO>>builder()
                .data(userHackathonService.getUserHackathons())
                .message("Get all UserHackathons")
                .build();
    }

    @GetMapping("/{Id}")
    public ApiResponse<UserHackathonResponseDTO> getUserHackathon(@PathVariable("Id") Long id) {
        return ApiResponse.<UserHackathonResponseDTO>builder()
                .data(userHackathonService.getUserHackathon(id))
                .message("Get UserHackathon by Id")
                .build();
    }

    @GetMapping("/hackathon/{hackathonId}")
    public ApiResponse<List<UserHackathonResponseDTO>> getUserHackathonsByHackathonId(
            @PathVariable("hackathonId") Long hackathonId) {
        List<UserHackathonResponseDTO> response = userHackathonService.getUserHackathonsByHackathonId(hackathonId);

        return ApiResponse.<List<UserHackathonResponseDTO>>builder()
                .data(response)
                .message("Get all UserHackathons by Hackathon ID")
                .build();
    }

    @GetMapping("/hackathon/{hackathonId}/roles")
    public ResponseEntity<List<UserHackathonResponseDTO>> getUserHackathonsByHackathonIdAndRoles(
            @PathVariable Long hackathonId, @RequestParam List<String> roles) {

        List<UserHackathonResponseDTO> userHackathons =
                userHackathonService.getUserHackathonsByHackathonIdAndRoles(hackathonId, roles);
        return ResponseEntity.ok(userHackathons);
    }

    @PutMapping("/{Id}")
     @PreAuthorize("hasAuthority('UPDATE_USER_HACKATHON')")
    public ApiResponse<UserHackathonResponseDTO> updateUserHackathon(
            @PathVariable("Id") Long id, @RequestBody UserHackathonRequestDTO request) {
        return ApiResponse.<UserHackathonResponseDTO>builder()
                .data(userHackathonService.updateUserHackathon(id, request))
                .message("UserHackathon updated successfully")
                .build();
    }

    @DeleteMapping("/{Id}")
     @PreAuthorize("hasAuthority('DELETE_USER_HACKATHON')")
    public ApiResponse<Void> deletePermission(@PathVariable("Id") Long id) {
        userHackathonService.deleteUserHackathon(id);
        return ApiResponse.<Void>builder()
                .message("UserHackathon has been deleted")
                .build();
    }
}
