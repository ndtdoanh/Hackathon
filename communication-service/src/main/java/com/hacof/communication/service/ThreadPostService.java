package com.hacof.communication.service;

import com.hacof.communication.dto.request.ThreadPostRequestDTO;
import com.hacof.communication.dto.response.ThreadPostResponseDTO;

import java.util.List;

public interface ThreadPostService {

    ThreadPostResponseDTO createThreadPost(ThreadPostRequestDTO requestDTO);

    List<ThreadPostResponseDTO> getAllThreadPosts();

    ThreadPostResponseDTO getThreadPost(Long id);

    ThreadPostResponseDTO updateThreadPost(Long id, ThreadPostRequestDTO requestDTO);

    void deleteThreadPost(Long id);
}
