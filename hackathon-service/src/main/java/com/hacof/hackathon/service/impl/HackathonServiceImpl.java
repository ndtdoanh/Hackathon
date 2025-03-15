package com.hacof.hackathon.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.service.HackathonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HackathonServiceImpl implements HackathonService {
    // private final HackathonMapper hackathonMapper;
    private final HackathonRepository hackathonRepository;

    @Override
    public List<HackathonDTO> getAllHackathons() {
        return null;
    }

    @Override
    public HackathonDTO getHackathonById(Long id) {
        return null;
    }

    @Override
    public HackathonDTO createHackathon(HackathonDTO hackathonDTO) {
        return null;
    }

    @Override
    public HackathonDTO updateHackathon(Long id, HackathonDTO hackathonDTO) {
        return null;
    }

    @Override
    public void deleteHackathon(Long id) {
        Hackathon hackathon = hackathonRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));
        hackathonRepository.delete(hackathon);
    }
}
