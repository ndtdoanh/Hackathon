package com.hacof.communication.services.impl;

import com.hacof.communication.dto.request.ForumthreadRequestDTO;
import com.hacof.communication.dto.response.ForumthreadResponseDTO;
import com.hacof.communication.entities.Forumthread;
import com.hacof.communication.entities.Hackathon;
import com.hacof.communication.repositories.ForumthreadRepository;
import com.hacof.communication.repositories.HackathonRepository;
import com.hacof.communication.services.ForumthreadService;
import com.hacof.communication.mapper.ForumthreadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ForumthreadServiceImpl implements ForumthreadService {

    private final ForumthreadRepository forumthreadRepository;
    private final HackathonRepository hackathonRepository;

    @Autowired
    public ForumthreadServiceImpl(ForumthreadRepository forumthreadRepository, HackathonRepository hackathonRepository) {
        this.forumthreadRepository = forumthreadRepository;
        this.hackathonRepository = hackathonRepository;
    }

    @Override
    public ForumthreadResponseDTO createForumthread(ForumthreadRequestDTO forumthreadRequestDTO) {
        Hackathon hackathon = hackathonRepository.findById(forumthreadRequestDTO.getHackathonId())
                .orElseThrow(() -> new IllegalArgumentException("Hackathon not found!"));

        Forumthread forumthread = ForumthreadMapper.toEntity(forumthreadRequestDTO);
        forumthread.setHackathon(hackathon);

        forumthread = forumthreadRepository.save(forumthread);
        return ForumthreadMapper.toDTO(forumthread);
    }


    @Override
    public Optional<ForumthreadResponseDTO> getForumthreadById(Long id) {
        Optional<Forumthread> forumthread = forumthreadRepository.findById(id);
        return forumthread.map(ForumthreadMapper::toDTO);
    }

    @Override
    public List<ForumthreadResponseDTO> getAllForumthreads() {
        return forumthreadRepository.findAll().stream()
                .map(ForumthreadMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ForumthreadResponseDTO updateForumthread(Long id, ForumthreadRequestDTO forumthreadRequestDTO) {
        Hackathon hackathon = hackathonRepository.findById(forumthreadRequestDTO.getHackathonId())
                .orElseThrow(() -> new IllegalArgumentException("Hackathon not found!"));

        Forumthread forumthread = forumthreadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Forumthread not found!"));

        forumthread.setTitle(forumthreadRequestDTO.getTitle());
        forumthread.setStatus(forumthreadRequestDTO.getStatus());
        forumthread.setHackathon(hackathon);

        forumthread = forumthreadRepository.save(forumthread);
        return ForumthreadMapper.toDTO(forumthread);
    }


    @Override
    public void deleteForumthread(Long id) {
        if (forumthreadRepository.existsById(id)) {
            forumthreadRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Forumthread with ID " + id + " not found!");
        }
    }
}
