package com.hacof.hackathon.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.AwardDTO;
import com.hacof.hackathon.entity.Award;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.AwardMapper;
import com.hacof.hackathon.repository.AwardRepository;
import com.hacof.hackathon.service.AwardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwardServiceImpl implements AwardService {
    private final AwardMapper awardMapper;
    private final AwardRepository awardRepository;

    @Override
    public List<AwardDTO> getAllAwards() {
        return awardRepository.findAll().stream().map(awardMapper::convertToDTO).toList();
    }

    @Override
    public AwardDTO getAwardById(Long id) {
        return awardRepository
                .findById(id)
                .map(awardMapper::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Award not found"));
    }

    @Override
    public AwardDTO createAward(AwardDTO awardDTO) {
        Award award = awardMapper.convertToEntity(awardDTO);
        Award savedAward = awardRepository.save(award);
        return awardMapper.convertToDTO(savedAward);
    }

    @Override
    public AwardDTO updateAward(Long id, AwardDTO awardDTO) {
        Award existingAward =
                awardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Award not found"));
        existingAward.setName(awardDTO.getName());
        existingAward.setDescription(awardDTO.getDescription());
        existingAward.setAmountPrize(awardDTO.getPrizeAmount());
        Award updatedAward = awardRepository.save(existingAward);
        return awardMapper.convertToDTO(updatedAward);
    }

    @Override
    public void deleteAward(Long id) {
        Award award = awardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Award not found"));
        awardRepository.delete(award);
    }
}
