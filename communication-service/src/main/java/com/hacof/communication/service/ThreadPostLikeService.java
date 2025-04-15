package com.hacof.communication.service;

import com.hacof.communication.dto.request.ThreadPostLikeRequestDTO;
import com.hacof.communication.dto.response.ThreadPostLikeResponseDTO;

import java.util.List;

public interface ThreadPostLikeService {
    ThreadPostLikeResponseDTO createThreadPostLike(ThreadPostLikeRequestDTO requestDTO);

    List<ThreadPostLikeResponseDTO> getAllThreadPostLikes();

    ThreadPostLikeResponseDTO getThreadPostLike(Long id);

    List<ThreadPostLikeResponseDTO> getLikesByThreadPostId(Long threadPostId);

    void deleteThreadPostLike(Long id);
}
