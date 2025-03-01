package com.hacof.communication.controllers;

import com.hacof.communication.dto.request.ForumthreadRequestDTO;
import com.hacof.communication.dto.response.ForumthreadResponseDTO;
import com.hacof.communication.responses.CommonResponse;
import com.hacof.communication.services.ForumthreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/forumthreads")
public class ForumthreadController {

    private final ForumthreadService forumthreadService;

    @Autowired
    public ForumthreadController(ForumthreadService forumthreadService) {
        this.forumthreadService = forumthreadService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<ForumthreadResponseDTO>> createForumthread(@RequestBody ForumthreadRequestDTO forumthreadRequestDTO) {
        ForumthreadResponseDTO createdForumthread = forumthreadService.createForumthread(forumthreadRequestDTO);
        CommonResponse<ForumthreadResponseDTO> response = new CommonResponse<>(HttpStatus.CREATED.value(), "Forum thread created successfully", createdForumthread, null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ForumthreadResponseDTO>> getForumthread(@PathVariable Long id) {
        Optional<ForumthreadResponseDTO> forumthread = forumthreadService.getForumthreadById(id);
        if (forumthread.isPresent()) {
            CommonResponse<ForumthreadResponseDTO> response = new CommonResponse<>(HttpStatus.OK.value(), "Forum thread retrieved successfully", forumthread.get(), null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CommonResponse<ForumthreadResponseDTO> response = new CommonResponse<>(HttpStatus.NOT_FOUND.value(), "Forum thread not found", null, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ForumthreadResponseDTO>>> getAllForumthreads() {
        List<ForumthreadResponseDTO> forumthreads = forumthreadService.getAllForumthreads();
        CommonResponse<List<ForumthreadResponseDTO>> response = new CommonResponse<>(HttpStatus.OK.value(), "Forum threads retrieved successfully", forumthreads, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<ForumthreadResponseDTO>> updateForumthread(@PathVariable Long id, @RequestBody ForumthreadRequestDTO forumthreadRequestDTO) {
        try {
            ForumthreadResponseDTO updatedForumthread = forumthreadService.updateForumthread(id, forumthreadRequestDTO);
            CommonResponse<ForumthreadResponseDTO> response = new CommonResponse<>(HttpStatus.OK.value(), "Forum thread updated successfully", updatedForumthread, null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            CommonResponse<ForumthreadResponseDTO> response = new CommonResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteForumthread(@PathVariable Long id) {
        try {
            forumthreadService.deleteForumthread(id);
            CommonResponse<Void> response = new CommonResponse<>(HttpStatus.NO_CONTENT.value(), "Forum thread deleted successfully", null, null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            CommonResponse<Void> response = new CommonResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
