package com.hacof.hackathon.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.HackathonMapper;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.service.HackathonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HackathonServiceImpl implements HackathonService {
    private final HackathonMapper hackathonMapper;
    private final HackathonRepository hackathonRepository;

    @Override
    public List<HackathonDTO> getAllHackathons() {
        return hackathonRepository.findAll().stream()
                .map(hackathonMapper::convertToDTO)
                .toList();
    }

    @Override
    public HackathonDTO getHackathonById(Long id) {
        return hackathonRepository
                .findById(id)
                .map(hackathonMapper::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));
    }

    @Override
    public HackathonDTO createHackathon(HackathonDTO hackathonDTO) {
        Hackathon hackathon = hackathonMapper.convertToEntity(hackathonDTO);
        Hackathon savedHackathon = hackathonRepository.save(hackathon);
        return hackathonMapper.convertToDTO(savedHackathon);
    }

    @Override
    public HackathonDTO updateHackathon(Long id, HackathonDTO hackathonDTO) {
        Hackathon existingHackathon = hackathonRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));
        existingHackathon.setName(hackathonDTO.getName());
        existingHackathon.setDescription(hackathonDTO.getDescription());
        existingHackathon.setStartDate(hackathonDTO.getStartDate());
        existingHackathon.setEndDate(hackathonDTO.getEndDate());
        Hackathon updatedHackathon = hackathonRepository.save(existingHackathon);
        return hackathonMapper.convertToDTO(updatedHackathon);
    }

    @Override
    public void deleteHackathon(Long id) {
        Hackathon hackathon = hackathonRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));
        hackathonRepository.delete(hackathon);
    }
}
