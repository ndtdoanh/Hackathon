package com.hacof.submission.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.submission.dto.request.JudgeSubmissionDetailRequestDTO;
import com.hacof.submission.dto.response.JudgeSubmissionDetailResponseDTO;
import com.hacof.submission.response.CommonResponse;
import com.hacof.submission.service.JudgeSubmissionDetailService;

@RestController
@RequestMapping("/api/v1/judgesubmissiondetails")
public class JudgeSubmissionDetailController {

    @Autowired
    private JudgeSubmissionDetailService service;

    @GetMapping
    public ResponseEntity<CommonResponse<List<JudgeSubmissionDetailResponseDTO>>> getAllDetails() {
        CommonResponse<List<JudgeSubmissionDetailResponseDTO>> response = new CommonResponse<>();
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all judge submission details successfully!");
            response.setData(service.getAllDetails());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<JudgeSubmissionDetailResponseDTO>> getDetailById(@PathVariable Long id) {
        CommonResponse<JudgeSubmissionDetailResponseDTO> response = new CommonResponse<>();
        try {
            JudgeSubmissionDetailResponseDTO detail = service.getDetailById(id);
            if (detail == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setMessage("Judge Submission Detail not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Judge Submission Detail fetched successfully");
            response.setData(detail);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<CommonResponse<JudgeSubmissionDetailResponseDTO>> createDetail(
            @RequestBody JudgeSubmissionDetailRequestDTO requestDTO) {
        CommonResponse<JudgeSubmissionDetailResponseDTO> response = new CommonResponse<>();
        try {
            JudgeSubmissionDetailResponseDTO createdDetail = service.createDetail(requestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Judge Submission Detail created successfully");
            response.setData(createdDetail);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<JudgeSubmissionDetailResponseDTO>> updateDetail(
            @PathVariable Long id, @RequestBody JudgeSubmissionDetailRequestDTO requestDTO) {
        CommonResponse<JudgeSubmissionDetailResponseDTO> response = new CommonResponse<>();
        try {
            JudgeSubmissionDetailResponseDTO updatedDetail = service.updateDetail(id, requestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Judge Submission Detail updated successfully");
            response.setData(updatedDetail);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Boolean>> deleteDetail(@PathVariable Long id) {
        CommonResponse<Boolean> response = new CommonResponse<>();
        try {
            boolean deleted = service.deleteDetail(id);
            if (!deleted) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setMessage("Judge Submission Detail not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Judge Submission Detail deleted successfully");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
