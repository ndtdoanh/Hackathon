package com.hacof.hackathon.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.hackathon.dto.MentorshipRequestDTO;
import com.hacof.hackathon.service.MentorshipRequestService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/mentorship-requests")
@RequiredArgsConstructor
@Slf4j
public class MentorshipRequestController {

    private final MentorshipRequestService mentorshipRequestService;

    @PostMapping("/book")
    public ResponseEntity<CommonResponse<MentorshipRequestDTO>> bookMentor(
            @RequestBody CommonRequest<MentorshipRequestDTO> request) {
        log.debug("Received request to book mentor: {}", request);
        MentorshipRequestDTO mentorshipRequestDTO = mentorshipRequestService.requestMentor(
                request.getData().getTeamId(), request.getData().getMentorId());
        CommonResponse<MentorshipRequestDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Mentor booked successfully"),
                mentorshipRequestDTO);
        log.debug("Booked mentor: {}", mentorshipRequestDTO);
        return ResponseEntity.ok(response);
    }
}
