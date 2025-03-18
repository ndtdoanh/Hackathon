package com.hacof.hackathon.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/judges")
@RequiredArgsConstructor
public class JudgeController {
    //    private final JudgeService judgeService;
    //
    //    @PostMapping
    //    // @PreAuthorize("hasAuthority('CREATE_JUDGE')")
    //    public ResponseEntity<CommonResponse<JudgeDTO>> createJudge(@RequestBody CommonRequest<JudgeDTO> request) {
    //        JudgeDTO judgeDTO = judgeService.createJudge(request.getData());
    //        CommonResponse<JudgeDTO> response = new CommonResponse<>(
    //                request.getRequestId(),
    //                LocalDateTime.now(),
    //                request.getChannel(),
    //                new CommonResponse.Result("0000", "Judge created successfully"),
    //                judgeDTO);
    //        return ResponseEntity.ok(response);
    //    }
    //
    //    @PostMapping("/assign")
    //    //  @PreAuthorize("hasAuthority('ASSIGN_JUDGE_TO_ROUND')")
    //    public ResponseEntity<CommonResponse<Void>> assignJudgeToRound(
    //            @RequestBody CommonRequest<Map<String, Long>> request) {
    //        Long judgeId = request.getData().get("judgeId");
    //        Long roundId = request.getData().get("roundId");
    //        judgeService.assignJudgeToRound(judgeId, roundId);
    //        CommonResponse<Void> response = new CommonResponse<>(
    //                request.getRequestId(),
    //                LocalDateTime.now(),
    //                request.getChannel(),
    //                new CommonResponse.Result("0000", "Judge assigned to round successfully"),
    //                null);
    //        return ResponseEntity.ok(response);
    //    }
}
