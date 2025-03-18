package com.hacof.communication.controller;

import com.hacof.communication.dto.request.ForumCategoryRequestDTO;
import com.hacof.communication.dto.response.ForumCategoryResponseDTO;
import com.hacof.communication.service.ForumCategoryService;
import com.hacof.communication.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/forum-categories")
public class ForumCategoryController {

    @Autowired
    private ForumCategoryService forumCategoryService;

    @PostMapping
    public ResponseEntity<CommonResponse<ForumCategoryResponseDTO>> createForumCategory(
            @RequestBody ForumCategoryRequestDTO forumCategoryRequestDTO) {
        CommonResponse<ForumCategoryResponseDTO> response = new CommonResponse<>();
        try {
            ForumCategoryResponseDTO createdCategory = forumCategoryService.createForumCategory(forumCategoryRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Forum category created successfully!");
            response.setData(createdCategory);
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
    public ResponseEntity<CommonResponse<List<ForumCategoryResponseDTO>>> getAllForumCategories() {
        CommonResponse<List<ForumCategoryResponseDTO>> response = new CommonResponse<>();
        try {
            List<ForumCategoryResponseDTO> categories = forumCategoryService.getAllForumCategories();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Forum categories fetched successfully!");
            response.setData(categories);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ForumCategoryResponseDTO>> getForumCategoryById(@PathVariable Long id) {
        CommonResponse<ForumCategoryResponseDTO> response = new CommonResponse<>();
        try {
            ForumCategoryResponseDTO category = forumCategoryService.getForumCategory(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Forum category fetched successfully!");
            response.setData(category);
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
    public ResponseEntity<CommonResponse<ForumCategoryResponseDTO>> updateForumCategory(
            @PathVariable Long id,
            @RequestBody ForumCategoryRequestDTO forumCategoryRequestDTO) {
        CommonResponse<ForumCategoryResponseDTO> response = new CommonResponse<>();
        try {
            ForumCategoryResponseDTO updatedCategory = forumCategoryService.updateForumCategory(id, forumCategoryRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Forum category updated successfully!");
            response.setData(updatedCategory);
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
    public ResponseEntity<CommonResponse<String>> deleteForumCategory(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            forumCategoryService.deleteForumCategory(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Forum category deleted successfully!");
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
