package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.request.ForumThreadRequestDTO;
import com.hacof.communication.dto.response.ForumThreadResponseDTO;

public interface ForumThreadService {

    ForumThreadResponseDTO createForumThread(ForumThreadRequestDTO forumThreadRequestDTO);

    ForumThreadResponseDTO updateForumThread(Long id, ForumThreadRequestDTO forumThreadRequestDTO);

    void deleteForumThread(Long id);

    ForumThreadResponseDTO getForumThread(Long id);

    List<ForumThreadResponseDTO> getAllForumThreads();

    List<ForumThreadResponseDTO> getForumThreadsByCategoryId(Long categoryId);
}
