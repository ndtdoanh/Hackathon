package com.hacof.communication.services;

import java.util.List;
import java.util.Optional;

import com.hacof.communication.dto.request.ForumcommentRequestDTO;
import com.hacof.communication.dto.response.ForumcommentResponseDTO;

public interface ForumcommentService {
    ForumcommentResponseDTO createForumcomment(ForumcommentRequestDTO forumcommentRequestDTO);

    Optional<ForumcommentResponseDTO> getForumcommentById(Long id);

    List<ForumcommentResponseDTO> getAllForumcomments();

    ForumcommentResponseDTO updateForumcomment(Long id, ForumcommentRequestDTO forumcommentRequestDTO);

    void deleteForumcomment(Long id);

    List<ForumcommentResponseDTO> getAllForumcommentsByThreadId(Long threadId);
}
