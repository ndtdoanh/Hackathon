package com.hacof.communication.controller;

import com.hacof.communication.dto.request.ThreadPostRequestDTO;
import com.hacof.communication.dto.response.ThreadPostResponseDTO;
import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.ThreadPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/thread-posts")
public class ThreadPostController {

    @Autowired
    private ThreadPostService threadPostService;

    @PostMapping
    public ResponseEntity<CommonResponse<ThreadPostResponseDTO>> createThreadPost(
            @RequestBody ThreadPostRequestDTO threadPostRequestDTO) {
        CommonResponse<ThreadPostResponseDTO> response = new CommonResponse<>();
        try {
            ThreadPostResponseDTO createdPost = threadPostService.createThreadPost(threadPostRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Thread post created successfully!");
            response.setData(createdPost);
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
    public ResponseEntity<CommonResponse<List<ThreadPostResponseDTO>>> getAllThreadPosts() {
        CommonResponse<List<ThreadPostResponseDTO>> response = new CommonResponse<>();
        try {
            List<ThreadPostResponseDTO> posts = threadPostService.getAllThreadPosts();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Thread posts fetched successfully!");
            response.setData(posts);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ThreadPostResponseDTO>> getThreadPostById(@PathVariable Long id) {
        CommonResponse<ThreadPostResponseDTO> response = new CommonResponse<>();
        try {
            ThreadPostResponseDTO post = threadPostService.getThreadPost(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Thread post fetched successfully!");
            response.setData(post);
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
    public ResponseEntity<CommonResponse<ThreadPostResponseDTO>> updateThreadPost(
            @PathVariable Long id,
            @RequestBody ThreadPostRequestDTO threadPostRequestDTO) {
        CommonResponse<ThreadPostResponseDTO> response = new CommonResponse<>();
        try {
            ThreadPostResponseDTO updatedPost = threadPostService.updateThreadPost(id, threadPostRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Thread post updated successfully!");
            response.setData(updatedPost);
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
    public ResponseEntity<CommonResponse<String>> deleteThreadPost(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            threadPostService.deleteThreadPost(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Thread post deleted successfully!");
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
