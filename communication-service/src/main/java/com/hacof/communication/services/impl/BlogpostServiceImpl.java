package com.hacof.communication.services.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.BlogpostRequestDTO;
import com.hacof.communication.dto.response.BlogpostResponseDTO;
import com.hacof.communication.entities.Blogpost;
import com.hacof.communication.entities.Hackathon;
import com.hacof.communication.entities.User;
import com.hacof.communication.mapper.BlogpostMapper;
import com.hacof.communication.repositories.BlogpostRepository;
import com.hacof.communication.repositories.HackathonRepository;
import com.hacof.communication.repositories.UserRepository;
import com.hacof.communication.services.BlogpostService;
import com.hacof.communication.utils.SecurityUtil;

@Service
public class BlogpostServiceImpl implements BlogpostService {

    @Autowired
    private BlogpostRepository blogpostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HackathonRepository hackathonRepository;

    @Autowired
    private BlogpostMapper blogpostMapper;

    @Override
    public BlogpostResponseDTO createBlogpost(BlogpostRequestDTO blogpostRequestDTO) {
        User author = userRepository
                .findById(blogpostRequestDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found!"));
        Hackathon hackathon = hackathonRepository
                .findById(blogpostRequestDTO.getHackathonId())
                .orElseThrow(() -> new RuntimeException("Hackathon not found!"));

        Blogpost blogpost = new Blogpost();
        blogpost.setTitle(blogpostRequestDTO.getTitle());
        blogpost.setContent(blogpostRequestDTO.getContent());
        blogpost.setAuthor(author);
        blogpost.setHackathon(hackathon);
        blogpost.setCreatedAt(Instant.now());
        blogpost.setUpdatedAt(Instant.now());
        blogpost.setCreatedBy(SecurityUtil.getCurrentUserLogin().orElse("system"));
        blogpost.setUpdatedBy(SecurityUtil.getCurrentUserLogin().orElse("system"));

        Blogpost savedBlogpost = blogpostRepository.save(blogpost);

        return blogpostMapper.toResponseDTO(savedBlogpost);
    }

    @Override
    public BlogpostResponseDTO getBlogpostById(Long id) {
        Optional<Blogpost> blogpostOpt = blogpostRepository.findById(id);
        if (blogpostOpt.isPresent()) {
            return blogpostMapper.toResponseDTO(blogpostOpt.get());
        } else {
            throw new RuntimeException("Blogpost not found with id: " + id);
        }
    }

    @Override
    public BlogpostResponseDTO updateBlogpost(Long id, BlogpostRequestDTO blogpostRequestDTO) {
        // Lấy Blogpost từ database
        Blogpost existingBlogpost = blogpostRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Blogpost not found with ID: " + id));

        // Cập nhật các trường của blogpost
        existingBlogpost.setTitle(blogpostRequestDTO.getTitle());
        existingBlogpost.setContent(blogpostRequestDTO.getContent());
        existingBlogpost.setUpdatedAt(Instant.now());
        existingBlogpost.setUpdatedBy(SecurityUtil.getCurrentUserLogin().orElse("system"));

        Blogpost updatedBlogpost = blogpostRepository.save(existingBlogpost);
        return blogpostMapper.toResponseDTO(updatedBlogpost);
    }

    @Override
    public void deleteBlogpost(Long id) {
        Blogpost existingBlogpost = blogpostRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Blogpost not found with ID: " + id));
        blogpostRepository.delete(existingBlogpost);
    }

    @Override
    public List<BlogpostResponseDTO> getAllBlogposts() {
        List<Blogpost> blogposts = blogpostRepository.findAll();
        return blogposts.stream().map(blogpostMapper::toResponseDTO).collect(Collectors.toList());
    }
}
