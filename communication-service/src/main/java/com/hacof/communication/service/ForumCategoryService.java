package com.hacof.communication.service;

import com.hacof.communication.dto.request.ForumCategoryRequestDTO;
import com.hacof.communication.dto.response.ForumCategoryResponseDTO;

import java.util.List;

public interface ForumCategoryService {
    ForumCategoryResponseDTO createForumCategory(ForumCategoryRequestDTO requestDTO);

    ForumCategoryResponseDTO updateForumCategory(long id, ForumCategoryRequestDTO requestDTO);

    void deleteForumCategory(long id);

    ForumCategoryResponseDTO getForumCategory(long id);

    List<ForumCategoryResponseDTO> getAllForumCategories();
}
