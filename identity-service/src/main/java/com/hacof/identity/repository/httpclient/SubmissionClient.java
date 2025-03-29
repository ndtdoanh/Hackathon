package com.hacof.identity.repository.httpclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.hacof.identity.config.AuthenticationRequestInterceptor;
import com.hacof.submission.dto.response.RoundMarkCriterionResponseDTO;
import com.hacof.submission.response.CommonResponse;

@FeignClient(
        name = "submission-service",
        configuration = {AuthenticationRequestInterceptor.class})
public interface SubmissionClient {
    @GetMapping("/api/v1/roundmarkcriteria")
    ResponseEntity<CommonResponse<List<RoundMarkCriterionResponseDTO>>> getAll();
}
