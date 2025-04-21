package com.hacof.communication.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.ThreadPostLikeRequestDTO;
import com.hacof.communication.dto.response.ThreadPostLikeResponseDTO;
import com.hacof.communication.entity.ThreadPost;
import com.hacof.communication.entity.ThreadPostLike;
import com.hacof.communication.entity.User;
import com.hacof.communication.mapper.ThreadPostLikeMapper;
import com.hacof.communication.repository.ThreadPostLikeRepository;
import com.hacof.communication.repository.ThreadPostRepository;
import com.hacof.communication.service.ThreadPostLikeService;
import com.hacof.communication.util.AuditContext;

@Service
public class ThreadPostLikeServiceImpl implements ThreadPostLikeService {

    @Autowired
    private ThreadPostLikeRepository threadPostLikeRepository;

    @Autowired
    private ThreadPostRepository threadPostRepository;

    @Override
    public ThreadPostLikeResponseDTO createThreadPostLike(ThreadPostLikeRequestDTO requestDTO) {
        Long threadPostId = Long.parseLong(requestDTO.getThreadPostId());
        ThreadPost threadPost = threadPostRepository
                .findById(threadPostId)
                .orElseThrow(() -> new IllegalArgumentException("ThreadPost not found with id " + threadPostId));

        User createdBy = AuditContext.getCurrentUser();

        ThreadPostLike threadPostLike = ThreadPostLikeMapper.toEntity(requestDTO, threadPost, createdBy);
        threadPostLike = threadPostLikeRepository.save(threadPostLike);

        return ThreadPostLikeMapper.toResponseDTO(threadPostLike);
    }

    @Override
    public List<ThreadPostLikeResponseDTO> getAllThreadPostLikes() {
        List<ThreadPostLike> threadPostLikes = threadPostLikeRepository.findAll();
        return threadPostLikes.stream().map(ThreadPostLikeMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<ThreadPostLikeResponseDTO> getThreadPostLike(Long id) {
        List<ThreadPostLike> threadPostLikes = threadPostLikeRepository.findByThreadPostId(id);
        if (threadPostLikes.isEmpty()) {
            return new ArrayList<>();
        }

        return threadPostLikes.stream().map(ThreadPostLikeMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<ThreadPostLikeResponseDTO> getLikesByThreadPostId(Long threadPostId) {
        List<ThreadPostLike> likes = threadPostLikeRepository.findByThreadPostId(threadPostId);
        if (likes.isEmpty()) {
            return new ArrayList<>();
        }
        return likes.stream().map(ThreadPostLikeMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteThreadPostLike(Long id) {
        ThreadPostLike threadPostLike = threadPostLikeRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ThreadPostLike not found with id " + id));

        // Xóa hoặc đánh dấu là đã xóa
        threadPostLikeRepository.delete(threadPostLike);
    }
}
