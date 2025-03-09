package com.hacof.communication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.dto.request.ForumcommentRequestDTO;
import com.hacof.communication.dto.response.ForumcommentResponseDTO;
import com.hacof.communication.responses.CommonResponse;
import com.hacof.communication.services.ForumcommentService;

@RestController
@RequestMapping("/api/v1/forumcomments")
public class ForumcommentController {

    private final ForumcommentService forumcommentService;

    @Autowired
    public ForumcommentController(ForumcommentService forumcommentService) {
        this.forumcommentService = forumcommentService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<ForumcommentResponseDTO>> createForumcomment(
            @RequestBody ForumcommentRequestDTO forumcommentRequestDTO) {
        CommonResponse<ForumcommentResponseDTO> response = new CommonResponse<>();
        try {
            ForumcommentResponseDTO createdForumcomment =
                    forumcommentService.createForumcomment(forumcommentRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Forum comment created successfully!");
            response.setData(createdForumcomment);
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
    public ResponseEntity<CommonResponse<ForumcommentResponseDTO>> getForumcommentById(@PathVariable Long id) {
        CommonResponse<ForumcommentResponseDTO> response = new CommonResponse<>();
        try {
            ForumcommentResponseDTO forumcomment = forumcommentService
                    .getForumcommentById(id)
                    .orElseThrow(() -> new RuntimeException("Forum comment not found!"));
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Forum comment retrieved successfully!");
            response.setData(forumcomment);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Forum comment not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ForumcommentResponseDTO>>> getAllForumcomments() {
        CommonResponse<List<ForumcommentResponseDTO>> response = new CommonResponse<>();
        try {
            List<ForumcommentResponseDTO> forumcomments = forumcommentService.getAllForumcomments();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all forum comments successfully!");
            response.setData(forumcomments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<ForumcommentResponseDTO>> updateForumcomment(
            @PathVariable Long id, @RequestBody ForumcommentRequestDTO forumcommentRequestDTO) {
        CommonResponse<ForumcommentResponseDTO> response = new CommonResponse<>();
        try {
            ForumcommentResponseDTO updatedForumcomment =
                    forumcommentService.updateForumcomment(id, forumcommentRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Forum comment updated successfully!");
            response.setData(updatedForumcomment);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Forum comment not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteForumcomment(@PathVariable Long id) {
        CommonResponse<Void> response = new CommonResponse<>();
        try {
            forumcommentService.deleteForumcomment(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Forum comment deleted successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Forum comment not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/thread/{threadId}")
    public ResponseEntity<CommonResponse<List<ForumcommentResponseDTO>>> getAllForumcommentsByThreadId(
            @PathVariable Long threadId) {
        CommonResponse<List<ForumcommentResponseDTO>> response = new CommonResponse<>();
        try {
            List<ForumcommentResponseDTO> forumcomments = forumcommentService.getAllForumcommentsByThreadId(threadId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Forum comments retrieved successfully!");
            response.setData(forumcomments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
