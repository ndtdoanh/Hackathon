package com.hacof.communication.service.impl;

import com.hacof.communication.dto.request.ForumCategoryRequestDTO;
import com.hacof.communication.dto.response.ForumCategoryResponseDTO;
import com.hacof.communication.entity.ForumCategory;
import com.hacof.communication.mapper.ForumCategoryMapper;
import com.hacof.communication.repository.ForumCategoryRepository;
import com.hacof.communication.service.ForumCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ForumCategoryServiceImpl implements ForumCategoryService {

    @Autowired
    private ForumCategoryRepository forumCategoryRepository;

    @Override
    public ForumCategoryResponseDTO createForumCategory(ForumCategoryRequestDTO requestDTO) {
        ForumCategory forumCategory = ForumCategoryMapper.toEntity(requestDTO);
        forumCategory = forumCategoryRepository.save(forumCategory);
        return ForumCategoryMapper.toResponseDTO(forumCategory);
    }

    @Override
    public ForumCategoryResponseDTO updateForumCategory(long id, ForumCategoryRequestDTO requestDTO) {
        Optional<ForumCategory> optionalForumCategory = forumCategoryRepository.findById(id);
        if (!optionalForumCategory.isPresent()) {
            throw new IllegalArgumentException("Forum category not found with id " + id);
        }

        ForumCategory forumCategory = optionalForumCategory.get();
        forumCategory.setName(requestDTO.getName());
        forumCategory.setDescription(requestDTO.getDescription());
        forumCategory.setSection(requestDTO.getSection());

        forumCategory = forumCategoryRepository.save(forumCategory);
        return ForumCategoryMapper.toResponseDTO(forumCategory);
    }

    @Override
    public void deleteForumCategory(long id) {
        Optional<ForumCategory> optionalForumCategory = forumCategoryRepository.findById(id);
        if (!optionalForumCategory.isPresent()) {
            throw new IllegalArgumentException("Forum category not found with id " + id);
        }

        forumCategoryRepository.deleteById(id);
    }

    @Override
    public ForumCategoryResponseDTO getForumCategory(long id) {
        Optional<ForumCategory> optionalForumCategory = forumCategoryRepository.findById(id);
        if (!optionalForumCategory.isPresent()) {
            throw new IllegalArgumentException("Forum category not found with id " + id);
        }

        return ForumCategoryMapper.toResponseDTO(optionalForumCategory.get());
    }

    @Override
    public List<ForumCategoryResponseDTO> getAllForumCategories() {
        List<ForumCategory> forumCategories = forumCategoryRepository.findAll();
        return forumCategories.stream()
                .map(ForumCategoryMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
