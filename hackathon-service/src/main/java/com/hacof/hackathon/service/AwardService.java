package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.AwardDTO;

public interface AwardService {
    List<AwardDTO> getAllAwards();

    AwardDTO getAwardById(Long id);

    AwardDTO createAward(AwardDTO awardDTO);

    AwardDTO updateAward(Long id, AwardDTO awardDTO);

    void deleteAward(Long id);
}
