package com.hacof.identity.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.identity.repository.httpclient.SubmissionClient;
import com.hacof.submission.dto.response.RoundMarkCriterionResponseDTO;
import com.hacof.submission.response.CommonResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/roundmarkcriteria")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoundMarkCriterionController {
    SubmissionClient submissionClient;

    @GetMapping
    public ResponseEntity<CommonResponse<List<RoundMarkCriterionResponseDTO>>> getAll() {
        return submissionClient.getAll();
    }
}
