package com.hacof.communication.services;

import com.hacof.communication.dto.request.ForumthreadRequestDTO;
import com.hacof.communication.dto.response.ForumthreadResponseDTO;
import java.util.List;
import java.util.Optional;

public interface ForumthreadService {
    ForumthreadResponseDTO createForumthread(ForumthreadRequestDTO forumthreadRequestDTO);
    Optional<ForumthreadResponseDTO> getForumthreadById(Long id);
    List<ForumthreadResponseDTO> getAllForumthreads();
    ForumthreadResponseDTO updateForumthread(Long id, ForumthreadRequestDTO forumthreadRequestDTO);
    void deleteForumthread(Long id);
}