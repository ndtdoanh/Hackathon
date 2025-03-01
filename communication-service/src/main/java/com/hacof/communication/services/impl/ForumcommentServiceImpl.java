package com.hacof.communication.services.impl;

import com.hacof.communication.dto.request.ForumcommentRequestDTO;
import com.hacof.communication.dto.response.ForumcommentResponseDTO;
import com.hacof.communication.entities.Forumcomment;
import com.hacof.communication.entities.Forumthread;
import com.hacof.communication.entities.User;
import com.hacof.communication.repositories.ForumcommentRepository;
import com.hacof.communication.repositories.ForumthreadRepository;
import com.hacof.communication.repositories.UserRepository;
import com.hacof.communication.services.ForumcommentService;
import com.hacof.communication.mapper.ForumcommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ForumcommentServiceImpl implements ForumcommentService {

    private final ForumcommentRepository forumcommentRepository;
    private final ForumthreadRepository forumthreadRepository;
    private final UserRepository userRepository;

    @Autowired
    public ForumcommentServiceImpl(ForumcommentRepository forumcommentRepository,
                                   ForumthreadRepository forumthreadRepository,
                                   UserRepository userRepository) {
        this.forumcommentRepository = forumcommentRepository;
        this.forumthreadRepository = forumthreadRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ForumcommentResponseDTO createForumcomment(ForumcommentRequestDTO forumcommentRequestDTO) {
        // Kiểm tra Forumthread tồn tại
        Forumthread forumthread = forumthreadRepository.findById(forumcommentRequestDTO.getThreadId())
                .orElseThrow(() -> new IllegalArgumentException("Forumthread not found"));

        // Kiểm tra User tồn tại
        User user = userRepository.findById(forumcommentRequestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Tạo Forumcomment mới
        Forumcomment forumcomment = ForumcommentMapper.toEntity(forumcommentRequestDTO);
        forumcomment.setThread(forumthread);
        forumcomment.setUser(user);

        // Lưu Forumcomment và trả về DTO
        forumcomment = forumcommentRepository.save(forumcomment);
        return ForumcommentMapper.toDTO(forumcomment);
    }

    @Override
    public Optional<ForumcommentResponseDTO> getForumcommentById(Long id) {
        Optional<Forumcomment> forumcomment = forumcommentRepository.findById(id);
        return forumcomment.map(ForumcommentMapper::toDTO);
    }

    @Override
    public List<ForumcommentResponseDTO> getAllForumcomments() {
        return forumcommentRepository.findAll().stream()
                .map(ForumcommentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ForumcommentResponseDTO updateForumcomment(Long id, ForumcommentRequestDTO forumcommentRequestDTO) {
        // Kiểm tra Forumcomment có tồn tại không
        Forumcomment forumcomment = forumcommentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Forumcomment not found"));

        // Kiểm tra Forumthread tồn tại
        Forumthread forumthread = forumthreadRepository.findById(forumcommentRequestDTO.getThreadId())
                .orElseThrow(() -> new IllegalArgumentException("Forumthread not found"));

        // Kiểm tra User tồn tại
        User user = userRepository.findById(forumcommentRequestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Cập nhật các trường trong Forumcomment
        forumcomment.setComment(forumcommentRequestDTO.getComment());
        forumcomment.setThread(forumthread);
        forumcomment.setUser(user);

        // Lưu và trả về DTO
        forumcomment = forumcommentRepository.save(forumcomment);
        return ForumcommentMapper.toDTO(forumcomment);
    }

    @Override
    public void deleteForumcomment(Long id) {
        if (forumcommentRepository.existsById(id)) {
            forumcommentRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Forumcomment not found");
        }
    }

    @Override
    public List<ForumcommentResponseDTO> getAllForumcommentsByThreadId(Long threadId) {
        List<Forumcomment> forumcomments = forumcommentRepository.findByThreadId(threadId);

        return forumcomments.stream()
                .map(ForumcommentMapper::toDTO)
                .collect(Collectors.toList());
    }
}
