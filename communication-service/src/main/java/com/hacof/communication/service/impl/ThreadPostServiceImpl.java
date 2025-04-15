package com.hacof.communication.service.impl;

import com.hacof.communication.dto.request.ThreadPostRequestDTO;
import com.hacof.communication.dto.response.ThreadPostResponseDTO;
import com.hacof.communication.entity.ForumThread;
import com.hacof.communication.entity.ThreadPost;
import com.hacof.communication.mapper.ThreadPostMapper;
import com.hacof.communication.repository.ForumThreadRepository;
import com.hacof.communication.repository.ThreadPostRepository;
import com.hacof.communication.service.ThreadPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThreadPostServiceImpl implements ThreadPostService {

    @Autowired
    private ThreadPostRepository threadPostRepository;

    @Autowired
    private ForumThreadRepository forumThreadRepository;

    @Override
    public ThreadPostResponseDTO createThreadPost(ThreadPostRequestDTO requestDTO) {
        Long forumThreadId = Long.parseLong(requestDTO.getForumThreadId());
        ForumThread forumThread = forumThreadRepository
                .findById(forumThreadId)
                .orElseThrow(() -> new IllegalArgumentException("ForumThread not found"));

        ThreadPost threadPost = ThreadPostMapper.toEntity(requestDTO, forumThread);
        threadPost = threadPostRepository.save(threadPost);
        return ThreadPostMapper.toResponseDTO(threadPost);
    }

    @Override
    public List<ThreadPostResponseDTO> getAllThreadPosts() {
        List<ThreadPost> threadPosts = threadPostRepository.findAll();
        return threadPosts.stream().map(ThreadPostMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public ThreadPostResponseDTO getThreadPost(Long id) {
        ThreadPost threadPost = threadPostRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ThreadPost not found with id " + id));

        return ThreadPostMapper.toResponseDTO(threadPost);
    }

    @Override
    public ThreadPostResponseDTO updateThreadPost(Long id, ThreadPostRequestDTO requestDTO) {
        ThreadPost threadPost = threadPostRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ThreadPost not found with id " + id));

        threadPost.setContent(requestDTO.getContent());
        threadPost = threadPostRepository.save(threadPost);
        return ThreadPostMapper.toResponseDTO(threadPost);
    }

    @Override
    public void deleteThreadPost(Long id) {
        ThreadPost threadPost = threadPostRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ThreadPost not found with id " + id));

        threadPost.setDeleted(true);
        threadPostRepository.save(threadPost);
    }
}
