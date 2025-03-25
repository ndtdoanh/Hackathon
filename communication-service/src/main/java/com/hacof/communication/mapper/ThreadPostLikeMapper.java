package com.hacof.communication.mapper;

import com.hacof.communication.dto.request.ThreadPostLikeRequestDTO;
import com.hacof.communication.dto.response.ThreadPostLikeResponseDTO;
import com.hacof.communication.entity.ThreadPost;
import com.hacof.communication.entity.ThreadPostLike;
import com.hacof.communication.entity.User;

public class ThreadPostLikeMapper {

    public static ThreadPostLike toEntity(ThreadPostLikeRequestDTO requestDTO, ThreadPost threadPost, User createdBy) {
        ThreadPostLike threadPostLike = new ThreadPostLike();
        threadPostLike.setThreadPost(threadPost);
        return threadPostLike;
    }

    public static ThreadPostLikeResponseDTO toResponseDTO(ThreadPostLike threadPostLike) {
        ThreadPostLikeResponseDTO responseDTO = new ThreadPostLikeResponseDTO();
        responseDTO.setId(threadPostLike.getId());
        responseDTO.setThreadPostId(threadPostLike.getThreadPost().getId());
        responseDTO.setCreatedBy(threadPostLike.getCreatedBy().getUsername()); // Chỉ lấy ID của User
        responseDTO.setCreatedDate(threadPostLike.getCreatedDate());
        responseDTO.setLastModifiedDate(threadPostLike.getLastModifiedDate());
        return responseDTO;
    }
}
