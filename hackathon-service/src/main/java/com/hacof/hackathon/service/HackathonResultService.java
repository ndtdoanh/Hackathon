package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.HackathonResultDTO;

public interface HackathonResultService {
    HackathonResultDTO create(HackathonResultDTO hackathonResultDTO);

    HackathonResultDTO update(String id, HackathonResultDTO hackathonResultDTO);

    void delete(String id);

    List<HackathonResultDTO> createBulk(List<HackathonResultDTO> hackathonResultDTOs);

    List<HackathonResultDTO> updateBulk(List<HackathonResultDTO> hackathonResultDTOs);

    List<HackathonResultDTO> getAllByHackathonId(String hackathonId);
}
