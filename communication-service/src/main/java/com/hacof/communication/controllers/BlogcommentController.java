package com.hacof.communication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.dto.request.BlogcommentRequestDTO;
import com.hacof.communication.dto.response.BlogcommentResponseDTO;
import com.hacof.communication.responses.CommonResponse;
import com.hacof.communication.services.BlogcommentService;

@RestController
@RequestMapping("/api/v1/blogcomments")
public class BlogcommentController {

    @Autowired
    private BlogcommentService blogcommentService;

    @GetMapping
    @PreAuthorize("hasAuthority('GET_BLOGCOMMENTS')")
    public ResponseEntity<CommonResponse<List<BlogcommentResponseDTO>>> getAllComments() {
        CommonResponse<List<BlogcommentResponseDTO>> response = new CommonResponse<>();
        try {
            List<BlogcommentResponseDTO> data = blogcommentService.getAllComments();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all comments successfully!");
            response.setData(data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GET_BLOGCOMMENT')")
    public ResponseEntity<CommonResponse<BlogcommentResponseDTO>> getCommentById(@PathVariable Long id) {
        CommonResponse<BlogcommentResponseDTO> response = new CommonResponse<>();
        try {
            BlogcommentResponseDTO data =
                    blogcommentService.getCommentById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched comment successfully!");
            response.setData(data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_BLOGCOMMENT')")
    public ResponseEntity<CommonResponse<BlogcommentResponseDTO>> createComment(
            @RequestBody BlogcommentRequestDTO blogcommentRequestDTO) {
        CommonResponse<BlogcommentResponseDTO> response = new CommonResponse<>();
        try {
            BlogcommentResponseDTO createdComment = blogcommentService.createComment(blogcommentRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Comment created successfully!");
            response.setData(createdComment);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_BLOGCOMMENT')")
    public ResponseEntity<CommonResponse<BlogcommentResponseDTO>> updateComment(
            @PathVariable Long id, @RequestBody BlogcommentRequestDTO blogcommentRequestDTO) {
        CommonResponse<BlogcommentResponseDTO> response = new CommonResponse<>();
        try {
            BlogcommentResponseDTO updatedComment = blogcommentService.updateComment(id, blogcommentRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Comment updated successfully!");
            response.setData(updatedComment);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_BLOGCOMMENT')")
    public ResponseEntity<CommonResponse<Void>> deleteComment(@PathVariable Long id) {
        CommonResponse<Void> response = new CommonResponse<>();
        try {
            blogcommentService.deleteComment(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Comment deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/byPostId/{postId}")
    @PreAuthorize("hasAuthority('GET_BLOGCOMMENTS_BY_POST')")
    public ResponseEntity<CommonResponse<List<BlogcommentResponseDTO>>> getCommentsByPostId(@PathVariable Long postId) {
        CommonResponse<List<BlogcommentResponseDTO>> response = new CommonResponse<>();
        try {
            List<BlogcommentResponseDTO> comments = blogcommentService.getCommentsByPostId(postId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all comments for the post successfully!");
            response.setData(comments);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
