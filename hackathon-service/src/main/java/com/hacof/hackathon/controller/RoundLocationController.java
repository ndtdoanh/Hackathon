package com.hacof.hackathon.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.service.RoundLocationService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/round-locations")
@RequiredArgsConstructor
public class RoundLocationController {
    private final RoundLocationService roundLocationService;

    @DeleteMapping("/round")
    public ResponseEntity<CommonResponse<RoundLocationDTO>> deleteByRoundId(
            @RequestBody CommonRequest<RoundLocationDTO> request) {
        roundLocationService.deleteByRoundId(Long.parseLong(request.getData().getRoundId()));
        CommonResponse<RoundLocationDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Round Location deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }
}
