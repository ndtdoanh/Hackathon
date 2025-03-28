package com.hacof.hackathon.controller;

// import com.hacof.hackathon.service.IndividualRegistrationRequestService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;
import com.hacof.hackathon.service.IndividualRegistrationRequestService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/individual-registrations")
@RequiredArgsConstructor
@Slf4j
public class IndividualRegistrationRequestController {
    private final IndividualRegistrationRequestService individualRegistrationRequestService;

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

    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> deleteIndividualRegistration(
            @RequestBody @Valid CommonRequest<Long> request) {
        individualRegistrationRequestService.delete(request.getData());
        CommonResponse<Void> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
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
}
