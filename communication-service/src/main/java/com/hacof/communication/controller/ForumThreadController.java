package com.hacof.communication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.dto.request.ForumThreadRequestDTO;
import com.hacof.communication.dto.response.ForumThreadResponseDTO;
import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.ForumThreadService;

@RestController
@RequestMapping("/api/v1/forum-threads")
public class ForumThreadController {

    @Autowired
    private ForumThreadService forumThreadService;

    @PostMapping
    public ResponseEntity<CommonResponse<ForumThreadResponseDTO>> createForumThread(
            @RequestBody ForumThreadRequestDTO forumThreadRequestDTO) {
        CommonResponse<ForumThreadResponseDTO> response = new CommonResponse<>();
        try {
            ForumThreadResponseDTO createdThread = forumThreadService.createForumThread(forumThreadRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Forum thread created successfully!");
            response.setData(createdThread);
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

    @GetMapping
    public ResponseEntity<CommonResponse<List<ForumThreadResponseDTO>>> getAllForumThreads() {
        CommonResponse<List<ForumThreadResponseDTO>> response = new CommonResponse<>();
        try {
            List<ForumThreadResponseDTO> threads = forumThreadService.getAllForumThreads();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Forum threads fetched successfully!");
            response.setData(threads);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ForumThreadResponseDTO>> getForumThreadById(@PathVariable Long id) {
        CommonResponse<ForumThreadResponseDTO> response = new CommonResponse<>();
        try {
            ForumThreadResponseDTO thread = forumThreadService.getForumThread(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Forum thread fetched successfully!");
            response.setData(thread);
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
    public ResponseEntity<CommonResponse<ForumThreadResponseDTO>> updateForumThread(
            @PathVariable Long id, @RequestBody ForumThreadRequestDTO forumThreadRequestDTO) {
        CommonResponse<ForumThreadResponseDTO> response = new CommonResponse<>();
        try {
            ForumThreadResponseDTO updatedThread = forumThreadService.updateForumThread(id, forumThreadRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Forum thread updated successfully!");
            response.setData(updatedThread);
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
    public ResponseEntity<CommonResponse<String>> deleteForumThread(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            forumThreadService.deleteForumThread(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Forum thread deleted successfully!");
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
