package com.hacof.communication.services;

import java.util.List;
import java.util.Optional;

import com.hacof.communication.dto.request.BlogcommentRequestDTO;
import com.hacof.communication.dto.response.BlogcommentResponseDTO;

public interface BlogcommentService {
    List<BlogcommentResponseDTO> getAllComments();

    Optional<BlogcommentResponseDTO> getCommentById(Long id);

    BlogcommentResponseDTO createComment(BlogcommentRequestDTO blogcommentRequestDTO);

    BlogcommentResponseDTO updateComment(Long id, BlogcommentRequestDTO blogcommentRequestDTO);

    void deleteComment(Long id);

    List<BlogcommentResponseDTO> getCommentsByPostId(Long postId);
}
