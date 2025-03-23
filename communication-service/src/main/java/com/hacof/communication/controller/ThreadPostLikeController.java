package com.hacof.communication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.dto.request.ThreadPostLikeRequestDTO;
import com.hacof.communication.dto.response.ThreadPostLikeResponseDTO;
import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.ThreadPostLikeService;

@RestController
@RequestMapping("/api/v1/thread-post-likes")
public class ThreadPostLikeController {

    @Autowired
    private ThreadPostLikeService threadPostLikeService;

    @PostMapping
    public ResponseEntity<CommonResponse<ThreadPostLikeResponseDTO>> createThreadPostLike(
            @RequestBody ThreadPostLikeRequestDTO requestDTO) {
        CommonResponse<ThreadPostLikeResponseDTO> response = new CommonResponse<>();
        try {
            ThreadPostLikeResponseDTO createdLike = threadPostLikeService.createThreadPostLike(requestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Thread post like created successfully!");
            response.setData(createdLike);
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

    // Lấy tất cả ThreadPostLikes
    @GetMapping
    public ResponseEntity<CommonResponse<List<ThreadPostLikeResponseDTO>>> getAllThreadPostLikes() {
        CommonResponse<List<ThreadPostLikeResponseDTO>> response = new CommonResponse<>();
        try {
            List<ThreadPostLikeResponseDTO> likes = threadPostLikeService.getAllThreadPostLikes();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Thread post likes fetched successfully!");
            response.setData(likes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Lấy ThreadPostLike theo ID
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ThreadPostLikeResponseDTO>> getThreadPostLikeById(@PathVariable Long id) {
        CommonResponse<ThreadPostLikeResponseDTO> response = new CommonResponse<>();
        try {
            ThreadPostLikeResponseDTO like = threadPostLikeService.getThreadPostLike(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Thread post like fetched successfully!");
            response.setData(like);
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

    @GetMapping("/thread-post/{threadPostId}")
    public ResponseEntity<CommonResponse<List<ThreadPostLikeResponseDTO>>> getAllLikesByThreadPost(
            @PathVariable Long threadPostId) {
        CommonResponse<List<ThreadPostLikeResponseDTO>> response = new CommonResponse<>();
        try {
            List<ThreadPostLikeResponseDTO> likes = threadPostLikeService.getLikesByThreadPostId(threadPostId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Thread post likes fetched successfully!");
            response.setData(likes);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Xóa ThreadPostLike theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteThreadPostLike(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            threadPostLikeService.deleteThreadPostLike(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Thread post like deleted successfully!");
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
