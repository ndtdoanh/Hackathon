package com.hacof.hackathon.controller;

// import com.hacof.hackathon.service.IndividualRegistrationRequestService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;
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
    public ResponseEntity<CommonResponse<IndividualRegistrationRequestDTO>> createIndividualRegistration(
            @RequestBody @Valid CommonRequest<IndividualRegistrationRequestDTO> request) {
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

    @PutMapping
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

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteIndividualRegistration(
            @RequestBody IndividualRegistrationRequestDTO request) {
        individualRegistrationRequestService.delete(Long.parseLong(request.getId()));
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

    @GetMapping("/filter-by-hackathon-and-status-approved")
    public ResponseEntity<CommonResponse<List<IndividualRegistrationRequestDTO>>> getAllByHackathonIdAndStatusApproved(
            @RequestParam String hackathonId) {
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
}
