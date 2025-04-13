package com.hacof.communication.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.dto.request.ThreadPostReportRequestDTO;
import com.hacof.communication.dto.response.ThreadPostReportResponseDTO;
import com.hacof.communication.util.CommonResponse;
import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.service.ThreadPostReportService;

@RestController
@RequestMapping("/api/v1/thread-post-reports")
public class ThreadPostReportController {

    @Autowired
    private ThreadPostReportService threadPostReportService;

    private void setCommonResponseFields(CommonResponse<?> response, CommonRequest<?> request) {
        response.setRequestId(request.getRequestId() != null ? request.getRequestId() : UUID.randomUUID().toString());
        response.setRequestDateTime(request.getRequestDateTime() != null ? request.getRequestDateTime() : LocalDateTime.now());
        response.setChannel(request.getChannel() != null ? request.getChannel() : "HACOF");
    }

    private void setDefaultResponseFields(CommonResponse<?> response) {
        response.setRequestId(UUID.randomUUID().toString());
        response.setRequestDateTime(LocalDateTime.now());
        response.setChannel("HACOF");
    }

    @PostMapping
    public ResponseEntity<CommonResponse<ThreadPostReportResponseDTO>> createThreadPostReport(
            @RequestBody CommonRequest<ThreadPostReportRequestDTO> request) {
        CommonResponse<ThreadPostReportResponseDTO> response = new CommonResponse<>();
        try {
            ThreadPostReportResponseDTO createdReport = threadPostReportService.createThreadPostReport(request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Thread post report created successfully!");
            response.setData(createdReport);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/thread-post/{threadPostId}")
    public ResponseEntity<CommonResponse<List<ThreadPostReportResponseDTO>>> getReportsByThreadPostId(
            @PathVariable Long threadPostId) {
        CommonResponse<List<ThreadPostReportResponseDTO>> response = new CommonResponse<>();
        try {
            List<ThreadPostReportResponseDTO> reports = threadPostReportService.getReportsByThreadPostId(threadPostId);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Reports fetched successfully!");
            response.setData(reports);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ThreadPostReportResponseDTO>> getThreadPostReportById(@PathVariable Long id) {
        CommonResponse<ThreadPostReportResponseDTO> response = new CommonResponse<>();
        try {
            ThreadPostReportResponseDTO report = threadPostReportService.getThreadPostReport(id);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Report fetched successfully!");
            response.setData(report);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<ThreadPostReportResponseDTO>> updateThreadPostReport(
            @PathVariable Long id, @RequestBody CommonRequest<ThreadPostReportRequestDTO> request) {
        CommonResponse<ThreadPostReportResponseDTO> response = new CommonResponse<>();
        try {
            ThreadPostReportResponseDTO updatedReport = threadPostReportService.updateThreadPostReport(id, request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Report updated successfully!");
            response.setData(updatedReport);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteThreadPostReport(@PathVariable Long id) {
        CommonResponse<Void> response = new CommonResponse<>();
        try {
            threadPostReportService.deleteThreadPostReport(id);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Report deleted successfully!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
