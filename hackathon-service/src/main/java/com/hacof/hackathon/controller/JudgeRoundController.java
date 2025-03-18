package com.hacof.hackathon.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.hackathon.dto.JudgeRoundDTO;
import com.hacof.hackathon.service.JudgeRoundService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/judge-rounds")
@RequiredArgsConstructor
@Slf4j
public class JudgeRoundController {

    private final JudgeRoundService judgeRoundService;

    @PostMapping("/assign")
    public ResponseEntity<CommonResponse<JudgeRoundDTO>> assignJudgeToRound(
            @RequestBody CommonRequest<JudgeRoundDTO> request) {
        log.debug("Received request to assign judge to round: {}", request);
        JudgeRoundDTO judgeRoundDTO = judgeRoundService.assignJudgeToRound(
                request.getData().getJudgeId(), request.getData().getRoundId());
        CommonResponse<JudgeRoundDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Judge assigned to round successfully"),
                judgeRoundDTO);
        log.debug("Assigned judge to round: {}", judgeRoundDTO);
        return ResponseEntity.ok(response);
    }
}
