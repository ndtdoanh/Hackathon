package com.hacof.hackathon.controller;

import java.time.LocalDateTime;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;
import com.hacof.hackathon.service.IndividualRegistrationRequestService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/individual-registrations")
@RequiredArgsConstructor
public class IndividualRegistrationRequestController {
    private final IndividualRegistrationRequestService requestService;

    @PostMapping("/approve")
    public ResponseEntity<CommonResponse<IndividualRegistrationRequestDTO>> approveRequest(
            @Valid @RequestBody CommonRequest<Long> request) {
        IndividualRegistrationRequestDTO result = requestService.approveRequest(request.getData(), request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Individual registration approved"),
                result));
    }

    @PostMapping("/reject")
    public ResponseEntity<CommonResponse<IndividualRegistrationRequestDTO>> rejectRequest(
            @Valid @RequestBody CommonRequest<Long> request) {
        IndividualRegistrationRequestDTO result = requestService.rejectRequest(request.getData(), request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Individual registration rejected"),
                result));
    }
}
