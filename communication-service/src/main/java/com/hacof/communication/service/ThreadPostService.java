package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.request.ThreadPostRequestDTO;
import com.hacof.communication.dto.response.ThreadPostResponseDTO;

public interface ThreadPostService {

    ThreadPostResponseDTO createThreadPost(ThreadPostRequestDTO requestDTO);

    List<ThreadPostResponseDTO> getAllThreadPosts();

    ThreadPostResponseDTO getThreadPost(Long id);

    ThreadPostResponseDTO updateThreadPost(Long id, ThreadPostRequestDTO requestDTO);

    ThreadPostResponseDTO deleteThreadPost(Long id);

    List<ThreadPostResponseDTO> getThreadPostsByForumThreadId(Long forumThreadId);
}
