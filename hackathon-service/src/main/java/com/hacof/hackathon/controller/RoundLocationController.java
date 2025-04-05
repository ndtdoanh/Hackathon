package com.hacof.hackathon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/location")
    public ResponseEntity<CommonResponse<RoundLocationDTO>> deleteByLocationId(
            @RequestBody CommonRequest<RoundLocationDTO> request) {
        roundLocationService.deleteByLocationId(Long.parseLong(request.getData().getLocationId()));
        CommonResponse<RoundLocationDTO> response = new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Round Location deleted successfully"), null);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/round")
    public ResponseEntity<CommonResponse<RoundLocationDTO>> deleteByRoundId(
            @RequestBody CommonRequest<RoundLocationDTO> request) {
        roundLocationService.deleteByRoundId(Long.parseLong(request.getData().getRoundId()));
        CommonResponse<RoundLocationDTO> response = new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Round Location deleted successfully"), null);
        return ResponseEntity.ok(response);
    }
}
