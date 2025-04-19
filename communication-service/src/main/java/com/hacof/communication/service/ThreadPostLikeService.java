package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.request.ThreadPostLikeRequestDTO;
import com.hacof.communication.dto.response.ThreadPostLikeResponseDTO;

public interface ThreadPostLikeService {
    ThreadPostLikeResponseDTO createThreadPostLike(ThreadPostLikeRequestDTO requestDTO);

    List<ThreadPostLikeResponseDTO> getAllThreadPostLikes();

    List<ThreadPostLikeResponseDTO> getThreadPostLike(Long id);

    List<ThreadPostLikeResponseDTO> getLikesByThreadPostId(Long threadPostId);

    void deleteThreadPostLike(Long id);
}
