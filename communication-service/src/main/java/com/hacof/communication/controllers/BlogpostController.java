package com.hacof.communication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.dto.request.BlogpostRequestDTO;
import com.hacof.communication.dto.response.BlogpostResponseDTO;
import com.hacof.communication.responses.CommonResponse;
import com.hacof.communication.services.BlogpostService;

@RestController
@RequestMapping("/api/v1/blogposts")
public class BlogpostController {

    @Autowired
    private BlogpostService blogpostService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_BLOGPOST')")
    public ResponseEntity<CommonResponse<BlogpostResponseDTO>> createBlogpost(
            @RequestBody BlogpostRequestDTO blogpostRequestDTO) {
        CommonResponse<BlogpostResponseDTO> response = new CommonResponse<>();
        try {
            BlogpostResponseDTO createdBlogpost = blogpostService.createBlogpost(blogpostRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Blogpost created successfully!");
            response.setData(createdBlogpost);
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

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GET_BLOGPOST')")
    public ResponseEntity<CommonResponse<BlogpostResponseDTO>> getBlogpostById(@PathVariable Long id) {
        CommonResponse<BlogpostResponseDTO> response = new CommonResponse<>();
        try {
            BlogpostResponseDTO blogpost = blogpostService.getBlogpostById(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched blogpost successfully!");
            response.setData(blogpost);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_BLOGPOST')")
    public ResponseEntity<CommonResponse<BlogpostResponseDTO>> updateBlogpost(
            @PathVariable Long id, @RequestBody BlogpostRequestDTO blogpostRequestDTO) {
        CommonResponse<BlogpostResponseDTO> response = new CommonResponse<>();
        try {
            BlogpostResponseDTO updatedBlogpost = blogpostService.updateBlogpost(id, blogpostRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Blogpost updated successfully!");
            response.setData(updatedBlogpost);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_BLOGPOST')")
    public ResponseEntity<CommonResponse<Void>> deleteBlogpost(@PathVariable Long id) {
        CommonResponse<Void> response = new CommonResponse<>();
        try {
            blogpostService.deleteBlogpost(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Blogpost deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Blogpost not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GET_BLOGPOSTS')")
    public ResponseEntity<CommonResponse<List<BlogpostResponseDTO>>> getAllBlogposts() {
        CommonResponse<List<BlogpostResponseDTO>> response = new CommonResponse<>();
        try {
            List<BlogpostResponseDTO> blogposts = blogpostService.getAllBlogposts();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all blogposts successfully!");
            response.setData(blogposts);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
