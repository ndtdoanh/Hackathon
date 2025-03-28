package com.hacof.communication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.dto.request.ThreadPostReportRequestDTO;
import com.hacof.communication.dto.response.ThreadPostReportResponseDTO;
import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.ThreadPostReportService;

@RestController
@RequestMapping("/api/v1/thread-post-reports")
public class ThreadPostReportController {

    @Autowired
    private ThreadPostReportService threadPostReportService;

    @PostMapping
    public ResponseEntity<CommonResponse<ThreadPostReportResponseDTO>> createThreadPostReport(
            @RequestBody ThreadPostReportRequestDTO requestDTO) {
        CommonResponse<ThreadPostReportResponseDTO> response = new CommonResponse<>();
        try {
            ThreadPostReportResponseDTO createdReport = threadPostReportService.createThreadPostReport(requestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Thread post report created successfully!");
            response.setData(createdReport);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
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
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Reports fetched successfully!");
            response.setData(reports);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
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
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Report fetched successfully!");
            response.setData(report);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<ThreadPostReportResponseDTO>> updateThreadPostReport(
            @PathVariable Long id, @RequestBody ThreadPostReportRequestDTO requestDTO) {
        CommonResponse<ThreadPostReportResponseDTO> response = new CommonResponse<>();
        try {
            ThreadPostReportResponseDTO updatedReport = threadPostReportService.updateThreadPostReport(id, requestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Report updated successfully!");
            response.setData(updatedReport);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
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
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Report deleted successfully!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
