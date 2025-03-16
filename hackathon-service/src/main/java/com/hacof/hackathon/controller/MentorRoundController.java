package com.hacof.hackathon.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.hackathon.dto.MentorRoundDTO;
import com.hacof.hackathon.service.MentorRoundService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/mentor-rounds")
@RequiredArgsConstructor
@Slf4j
public class MentorRoundController {

    private final MentorRoundService mentorRoundService;

    @PostMapping("/assign")
    public ResponseEntity<CommonResponse<MentorRoundDTO>> assignMentorToRound(
            @RequestBody CommonRequest<MentorRoundDTO> request) {
        log.debug("Received request to assign mentor to round: {}", request);
        MentorRoundDTO mentorRoundDTO = mentorRoundService.assignMentorToRound(
                request.getData().getMentorId(), request.getData().getRoundId());
        CommonResponse<MentorRoundDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Mentor assigned to round successfully"),
                mentorRoundDTO);
        log.debug("Assigned mentor to round: {}", mentorRoundDTO);
        return ResponseEntity.ok(response);
    }
}
