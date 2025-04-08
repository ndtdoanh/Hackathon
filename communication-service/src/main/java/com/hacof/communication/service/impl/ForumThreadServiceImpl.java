package com.hacof.communication.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.ForumThreadRequestDTO;
import com.hacof.communication.dto.response.ForumThreadResponseDTO;
import com.hacof.communication.entity.ForumCategory;
import com.hacof.communication.entity.ForumThread;
import com.hacof.communication.mapper.ForumThreadMapper;
import com.hacof.communication.repository.ForumCategoryRepository;
import com.hacof.communication.repository.ForumThreadRepository;
import com.hacof.communication.repository.UserRepository;
import com.hacof.communication.service.ForumThreadService;

@Service
public class ForumThreadServiceImpl implements ForumThreadService {

    @Autowired
    private ForumThreadRepository forumThreadRepository;

    @Autowired
    private ForumCategoryRepository forumCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ForumThreadResponseDTO createForumThread(ForumThreadRequestDTO forumThreadRequestDTO) {
        // Validate title and forumCategoryId
        if (forumThreadRequestDTO.getTitle() == null || forumThreadRequestDTO.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
        if (forumThreadRequestDTO.getForumCategoryId() == null || forumThreadRequestDTO.getForumCategoryId().isEmpty()) {
            throw new IllegalArgumentException("Forum category ID cannot be null or empty.");
        }

        // Check if the ForumCategory exists
        Optional<ForumCategory> forumCategoryOptional = forumCategoryRepository.findById(Long.parseLong(forumThreadRequestDTO.getForumCategoryId()));
        if (!forumCategoryOptional.isPresent()) {
            throw new IllegalArgumentException("ForumCategory not found!");
        }
        ForumCategory forumCategory = forumCategoryOptional.get();

        if (forumThreadRepository.existsByTitleAndForumCategoryId(forumThreadRequestDTO.getTitle(), forumCategory.getId())) {
            throw new IllegalArgumentException("A thread with the same title already exists in this category.");
        }

        ForumThread forumThread = ForumThreadMapper.toEntity(forumThreadRequestDTO, forumCategory);
        forumThread = forumThreadRepository.save(forumThread);

        return ForumThreadMapper.toResponseDTO(forumThread);
    }

    @Override
    public ForumThreadResponseDTO updateForumThread(Long id, ForumThreadRequestDTO forumThreadRequestDTO) {
        if (forumThreadRequestDTO.getTitle() == null || forumThreadRequestDTO.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
        if (forumThreadRequestDTO.getForumCategoryId() == null || forumThreadRequestDTO.getForumCategoryId().isEmpty()) {
            throw new IllegalArgumentException("Forum category ID cannot be null or empty.");
        }

        Optional<ForumThread> forumThreadOptional = forumThreadRepository.findById(id);
        if (!forumThreadOptional.isPresent()) {
            throw new IllegalArgumentException("ForumThread not found!");
        }
        ForumThread forumThread = forumThreadOptional.get();

        // Check if the ForumCategory exists
        Optional<ForumCategory> forumCategoryOptional = forumCategoryRepository.findById(Long.parseLong(forumThreadRequestDTO.getForumCategoryId()));
        if (!forumCategoryOptional.isPresent()) {
            throw new IllegalArgumentException("ForumCategory not found!");
        }
        ForumCategory forumCategory = forumCategoryOptional.get();

        if (forumThreadRepository.existsByTitleAndForumCategoryId(forumThreadRequestDTO.getTitle(), forumCategory.getId())) {
            throw new IllegalArgumentException("A thread with the same title already exists in this category.");
        }

        forumThread.setTitle(forumThreadRequestDTO.getTitle());
        forumThread.setForumCategory(forumCategory);
        forumThread.setLocked(forumThreadRequestDTO.isLocked());
        forumThread.setPinned(forumThreadRequestDTO.isPinned());

        forumThread = forumThreadRepository.save(forumThread);

        return ForumThreadMapper.toResponseDTO(forumThread);
    }

    @Override
    public void deleteForumThread(Long id) {
        Optional<ForumThread> forumThreadOptional = forumThreadRepository.findById(id);
        if (!forumThreadOptional.isPresent()) {
            throw new IllegalArgumentException("ForumThread not found!");
        }
        ForumThread forumThread = forumThreadOptional.get();

        if (forumThread.getThreadPosts() != null && !forumThread.getThreadPosts().isEmpty()) {
            throw new IllegalArgumentException("Cannot delete ForumThread because it contains thread posts!");
        }
        forumThreadRepository.deleteById(id);
    }

    @Override
    public ForumThreadResponseDTO getForumThread(Long id) {
        Optional<ForumThread> forumThreadOptional = forumThreadRepository.findById(id);
        if (!forumThreadOptional.isPresent()) {
            throw new IllegalArgumentException("ForumThread not found!");
        }
        return ForumThreadMapper.toResponseDTO(forumThreadOptional.get());
    }

    @Override
    public List<ForumThreadResponseDTO> getAllForumThreads() {
        List<ForumThread> forumThreads = forumThreadRepository.findAll();
        return forumThreads.stream().map(ForumThreadMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<ForumThreadResponseDTO> getForumThreadsByCategoryId(Long categoryId) {
        List<ForumThread> forumThreads = forumThreadRepository.findByForumCategoryId(categoryId);
        if (forumThreads.isEmpty()) {
            throw new IllegalArgumentException("No forum threads found for category ID: " + categoryId);
        }
        return forumThreads.stream().map(ForumThreadMapper::toResponseDTO).collect(Collectors.toList());
    }
}
