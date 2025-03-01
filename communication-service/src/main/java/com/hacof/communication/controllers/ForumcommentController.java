package com.hacof.communication.controllers;

import com.hacof.communication.dto.request.ForumcommentRequestDTO;
import com.hacof.communication.dto.response.ForumcommentResponseDTO;
import com.hacof.communication.responses.CommonResponse;
import com.hacof.communication.services.ForumcommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/forumcomments")
public class ForumcommentController {

    private final ForumcommentService forumcommentService;

    @Autowired
    public ForumcommentController(ForumcommentService forumcommentService) {
        this.forumcommentService = forumcommentService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<ForumcommentResponseDTO>> createForumcomment(@RequestBody ForumcommentRequestDTO forumcommentRequestDTO) {
        ForumcommentResponseDTO createdForumcomment = forumcommentService.createForumcomment(forumcommentRequestDTO);
        CommonResponse<ForumcommentResponseDTO> response = new CommonResponse<>(HttpStatus.CREATED.value(), "Forum comment created successfully", createdForumcomment, null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ForumcommentResponseDTO>> getForumcomment(@PathVariable Long id) {
        Optional<ForumcommentResponseDTO> forumcomment = forumcommentService.getForumcommentById(id);
        if (forumcomment.isPresent()) {
            CommonResponse<ForumcommentResponseDTO> response = new CommonResponse<>(HttpStatus.OK.value(), "Forum comment retrieved successfully", forumcomment.get(), null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CommonResponse<ForumcommentResponseDTO> response = new CommonResponse<>(HttpStatus.NOT_FOUND.value(), "Forum comment not found", null, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ForumcommentResponseDTO>>> getAllForumcomments() {
        List<ForumcommentResponseDTO> forumcomments = forumcommentService.getAllForumcomments();
        CommonResponse<List<ForumcommentResponseDTO>> response = new CommonResponse<>(HttpStatus.OK.value(), "Forum comments retrieved successfully", forumcomments, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<ForumcommentResponseDTO>> updateForumcomment(@PathVariable Long id, @RequestBody ForumcommentRequestDTO forumcommentRequestDTO) {
        try {
            ForumcommentResponseDTO updatedForumcomment = forumcommentService.updateForumcomment(id, forumcommentRequestDTO);
            CommonResponse<ForumcommentResponseDTO> response = new CommonResponse<>(HttpStatus.OK.value(), "Forum comment updated successfully", updatedForumcomment, null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            CommonResponse<ForumcommentResponseDTO> response = new CommonResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteForumcomment(@PathVariable Long id) {
        try {
            forumcommentService.deleteForumcomment(id);
            CommonResponse<Void> response = new CommonResponse<>(HttpStatus.NO_CONTENT.value(), "Forum comment deleted successfully", null, null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            CommonResponse<Void> response = new CommonResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/thread/{threadId}")
    public ResponseEntity<CommonResponse<List<ForumcommentResponseDTO>>> getAllForumcommentsByThreadId(
            @PathVariable Long threadId) {
        List<ForumcommentResponseDTO> forumcomments = forumcommentService.getAllForumcommentsByThreadId(threadId);
        CommonResponse<List<ForumcommentResponseDTO>> response = new CommonResponse<>(
                HttpStatus.OK.value(),
                "Forum comments retrieved successfully",
                forumcomments,
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
