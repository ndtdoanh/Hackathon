package com.hacof.communication.services.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.BlogcommentRequestDTO;
import com.hacof.communication.dto.response.BlogcommentResponseDTO;
import com.hacof.communication.entities.Blogcomment;
import com.hacof.communication.entities.Blogpost;
import com.hacof.communication.entities.User;
import com.hacof.communication.mapper.BlogcommentMapper;
import com.hacof.communication.repositories.BlogcommentRepository;
import com.hacof.communication.repositories.BlogpostRepository;
import com.hacof.communication.repositories.UserRepository;
import com.hacof.communication.services.BlogcommentService;
import com.hacof.communication.utils.SecurityUtil;

@Service
public class BlogcommentServiceImpl implements BlogcommentService {

    @Autowired
    private BlogcommentRepository blogcommentRepository;

    @Autowired
    private BlogpostRepository blogpostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogcommentMapper blogcommentMapper;

    @Override
    public List<BlogcommentResponseDTO> getAllComments() {
        List<Blogcomment> comments = blogcommentRepository.findAll();
        return comments.stream().map(blogcommentMapper::toResponseDTO).toList();
    }

    @Override
    public Optional<BlogcommentResponseDTO> getCommentById(Long id) {
        Optional<Blogcomment> comment = blogcommentRepository.findById(id);
        return comment.map(blogcommentMapper::toResponseDTO);
    }

    @Override
    public BlogcommentResponseDTO createComment(BlogcommentRequestDTO blogcommentRequestDTO) {
        Blogpost post = blogpostRepository
                .findById(blogcommentRequestDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Blog post not found!"));

        User user = userRepository
                .findById(blogcommentRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Blogcomment blogcomment = blogcommentMapper.toEntity(blogcommentRequestDTO);
        blogcomment.setPost(post);
        blogcomment.setUser(user);
        blogcomment.setCreatedAt(Instant.now());
        blogcomment.setCreatedBy(SecurityUtil.getCurrentUserLogin().orElse("system"));

        blogcommentRepository.save(blogcomment);
        return blogcommentMapper.toResponseDTO(blogcomment);
    }

    @Override
    public BlogcommentResponseDTO updateComment(Long id, BlogcommentRequestDTO blogcommentRequestDTO) {
        Blogcomment existingComment =
                blogcommentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found!"));

        existingComment.setComment(blogcommentRequestDTO.getComment());
        existingComment.setUpdatedAt(Instant.now());
        existingComment.setUpdatedBy(SecurityUtil.getCurrentUserLogin().orElse("system"));

        blogcommentRepository.save(existingComment);
        return blogcommentMapper.toResponseDTO(existingComment);
    }

    @Override
    public void deleteComment(Long id) {
        Blogcomment comment =
                blogcommentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found!"));

        blogcommentRepository.delete(comment);
    }

    @Override
    public List<BlogcommentResponseDTO> getCommentsByPostId(Long postId) {
        List<Blogcomment> blogcomments = blogcommentRepository.findByPostId(postId);
        if (blogcomments.isEmpty()) {
            throw new RuntimeException("No comments found for the post with ID: " + postId);
        }
        return blogcomments.stream().map(blogcommentMapper::toResponseDTO).collect(Collectors.toList());
    }
}
