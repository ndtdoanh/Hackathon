package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

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
import com.hacof.hackathon.dto.IndividualRegistrationBulkRequestDTO;
import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.service.IndividualRegistrationRequestService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/individuals")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class IndividualRegistrationRequestController {
    IndividualRegistrationRequestService individualRegistrationRequestService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_INDIVIDUAL_REGISTRATION')")
    public ResponseEntity<CommonResponse<IndividualRegistrationRequestDTO>> createIndividualRegistration(
            @RequestBody @Valid CommonRequest<IndividualRegistrationRequestDTO> request) {
        if (request.getData() == null || request.getData().getHackathonId() == null) {
            throw new InvalidInputException("Hackathon ID cannot be null");
        }
        IndividualRegistrationRequestDTO individualRegistrationRequestDTO =
                individualRegistrationRequestService.create(request.getData());
        CommonResponse<IndividualRegistrationRequestDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Individual registration created successfully"),
                individualRegistrationRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bulk")
    @PreAuthorize("hasAuthority('CREATE_BULK_INDIVIDUAL_REGISTRATION')")
    public ResponseEntity<CommonResponse<List<IndividualRegistrationRequestDTO>>> createBulkIndividualRegistration(
            @RequestBody @Valid CommonRequest<List<IndividualRegistrationBulkRequestDTO>> request) {

        if (request.getData() == null || request.getData().isEmpty()) {
            throw new InvalidInputException("Request data cannot be empty");
        }

        List<IndividualRegistrationRequestDTO> createdRequests =
                individualRegistrationRequestService.createBulk(request.getData());

        CommonResponse<List<IndividualRegistrationRequestDTO>> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Bulk individual registration created successfully"),
                createdRequests);

        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_INDIVIDUAL_REGISTRATION')")
    public ResponseEntity<CommonResponse<IndividualRegistrationRequestDTO>> updateIndividualRegistration(
            @RequestBody @Valid CommonRequest<IndividualRegistrationRequestDTO> request) {
        IndividualRegistrationRequestDTO individualRegistrationRequestDTO = individualRegistrationRequestService.update(
                Long.parseLong(request.getData().getId()), request.getData());
        CommonResponse<IndividualRegistrationRequestDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Individual registration updated successfully"),
                individualRegistrationRequestDTO);
        return ResponseEntity.ok(response);
    }

    // update 23/4/25
    @PutMapping("/bulk-update")
    @PreAuthorize("hasAuthority('UPDATE_BULK_INDIVIDUAL_REGISTRATION')")
    public ResponseEntity<CommonResponse<List<IndividualRegistrationRequestDTO>>> bulkUpdateIndividualRegistrations(
            @RequestBody @Valid CommonRequest<List<IndividualRegistrationRequestDTO>> request) {
        List<IndividualRegistrationRequestDTO> updatedRequests =
                individualRegistrationRequestService.bulkUpdate(request.getData());
        CommonResponse<List<IndividualRegistrationRequestDTO>> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Bulk update successful"),
                updatedRequests);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_INDIVIDUAL_REGISTRATION')")
    public ResponseEntity<CommonResponse<Void>> deleteIndividualRegistration(@PathVariable Long id) {
        individualRegistrationRequestService.delete(id);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Individual registration deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<IndividualRegistrationRequestDTO>>> getAllIndividualRegistrations() {
        List<IndividualRegistrationRequestDTO> individualRegistrations = individualRegistrationRequestService.getAll();
        CommonResponse<List<IndividualRegistrationRequestDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all individual registrations successfully"),
                individualRegistrations);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<IndividualRegistrationRequestDTO>> getIndividualRegistrationById(
            @PathVariable Long id) {
        IndividualRegistrationRequestDTO individualRegistration = individualRegistrationRequestService.getById(id);
        CommonResponse<IndividualRegistrationRequestDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched individual registration successfully"),
                individualRegistration);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter-by-username")
    public ResponseEntity<CommonResponse<List<IndividualRegistrationRequestDTO>>> getAllByCreatedByUsername(
            @RequestParam String createdByUsername) {
        List<IndividualRegistrationRequestDTO> requests =
                individualRegistrationRequestService.getAllByCreatedByUsername(createdByUsername);
        CommonResponse<List<IndividualRegistrationRequestDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched individual registration requests successfully"),
                requests);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter-by-username-and-hackathon")
    public ResponseEntity<CommonResponse<List<IndividualRegistrationRequestDTO>>>
            getAllByCreatedByUsernameAndHackathonId(
                    @RequestParam String createdByUsername, @RequestParam String hackathonId) {
        List<IndividualRegistrationRequestDTO> requests =
                individualRegistrationRequestService.getAllByCreatedByUsernameAndHackathonId(
                        createdByUsername, hackathonId);
        CommonResponse<List<IndividualRegistrationRequestDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched individual registration requests successfully"),
                requests);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter-by-hackathon")
    public ResponseEntity<CommonResponse<List<IndividualRegistrationRequestDTO>>> getAllByHackathonId(
            @RequestParam String hackathonId) {
        List<IndividualRegistrationRequestDTO> requests =
                individualRegistrationRequestService.getAllByHackathonId(hackathonId);
        CommonResponse<List<IndividualRegistrationRequestDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched individual registration requests successfully"),
                requests);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter-by-hackathon-and-status-completed")
    public ResponseEntity<CommonResponse<List<IndividualRegistrationRequestDTO>>> getAllByHackathonIdAndStatusCompleted(
            @RequestParam String hackathonId) {
        if (!hackathonId.matches("\\d+")) { // Validate that hackathonId is numeric
            throw new InvalidInputException("Hackathon ID must be a numeric value");
        }

        List<IndividualRegistrationRequestDTO> requests =
                individualRegistrationRequestService.getAllByHackathonIdAndStatusCompleted(hackathonId);
        CommonResponse<List<IndividualRegistrationRequestDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched individual registration requests successfully"),
                requests);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter-by-hackathon-and-status-approved")
    public ResponseEntity<CommonResponse<List<IndividualRegistrationRequestDTO>>> getAllByHackathonIdAndStatusApproved(
            @RequestParam String hackathonId) {
        if (!hackathonId.matches("\\d+")) { // Validate that hackathonId is numeric
            throw new InvalidInputException("Hackathon ID must be a numeric value");
        }

        List<IndividualRegistrationRequestDTO> requests =
                individualRegistrationRequestService.getAllByHackathonIdAndStatusApproved(hackathonId);
        CommonResponse<List<IndividualRegistrationRequestDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched individual registration requests successfully"),
                requests);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter-by-hackathon-and-status-pending")
    public ResponseEntity<CommonResponse<List<IndividualRegistrationRequestDTO>>> getAllByHackathonIdAndStatusPending(
            @RequestParam String hackathonId) {
        if (!hackathonId.matches("\\d+")) {
            throw new InvalidInputException("Hackathon ID must be a numeric value");
        }

        List<IndividualRegistrationRequestDTO> requests =
                individualRegistrationRequestService.getAllByHackathonIdAndStatusPending(hackathonId);
        CommonResponse<List<IndividualRegistrationRequestDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched individual registration requests successfully"),
                requests);
        return ResponseEntity.ok(response);
    }
}
