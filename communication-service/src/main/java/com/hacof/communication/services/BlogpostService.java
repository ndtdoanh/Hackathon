package com.hacof.communication.services;

import com.hacof.communication.dto.request.BlogpostRequestDTO;
import com.hacof.communication.dto.response.BlogpostResponseDTO;

import java.util.List;

public interface BlogpostService {
    BlogpostResponseDTO createBlogpost(BlogpostRequestDTO blogpostRequestDTO);
    BlogpostResponseDTO getBlogpostById(Long id);
    BlogpostResponseDTO updateBlogpost(Long id, BlogpostRequestDTO blogpostRequestDTO);
    void deleteBlogpost(Long id);
    List<BlogpostResponseDTO> getAllBlogposts();
}
