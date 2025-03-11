package com.hacof.communication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.dto.request.ForumthreadRequestDTO;
import com.hacof.communication.dto.response.ForumthreadResponseDTO;
import com.hacof.communication.responses.CommonResponse;
import com.hacof.communication.services.ForumthreadService;

@RestController
@RequestMapping("/api/v1/forumthreads")
public class ForumthreadController {

    private final ForumthreadService forumthreadService;

    @Autowired
    public ForumthreadController(ForumthreadService forumthreadService) {
        this.forumthreadService = forumthreadService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<ForumthreadResponseDTO>> createForumthread(
            @RequestBody ForumthreadRequestDTO forumthreadRequestDTO) {
        CommonResponse<ForumthreadResponseDTO> response = new CommonResponse<>();
        try {
            ForumthreadResponseDTO createdForumthread = forumthreadService.createForumthread(forumthreadRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Forum thread created successfully!");
            response.setData(createdForumthread);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ForumthreadResponseDTO>> getForumthreadById(@PathVariable Long id) {
        CommonResponse<ForumthreadResponseDTO> response = new CommonResponse<>();
        try {
            ForumthreadResponseDTO forumthread = forumthreadService
                    .getForumthreadById(id)
                    .orElseThrow(() -> new RuntimeException("Forum thread not found"));

            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched forum thread successfully!");
            response.setData(forumthread);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Forum thread not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GET_FORUMTHREADS')")
    public ResponseEntity<CommonResponse<List<ForumthreadResponseDTO>>> getAllForumthreads() {
        CommonResponse<List<ForumthreadResponseDTO>> response = new CommonResponse<>();
        try {
            List<ForumthreadResponseDTO> forumthreads = forumthreadService.getAllForumthreads();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all forum threads successfully!");
            response.setData(forumthreads);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<ForumthreadResponseDTO>> updateForumthread(
            @PathVariable Long id, @RequestBody ForumthreadRequestDTO forumthreadRequestDTO) {
        CommonResponse<ForumthreadResponseDTO> response = new CommonResponse<>();
        try {
            ForumthreadResponseDTO updatedForumthread = forumthreadService.updateForumthread(id, forumthreadRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Forum thread updated successfully!");
            response.setData(updatedForumthread);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Forum thread not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteForumthread(@PathVariable Long id) {
        CommonResponse<Void> response = new CommonResponse<>();
        try {
            forumthreadService.deleteForumthread(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Forum thread deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Forum thread not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
