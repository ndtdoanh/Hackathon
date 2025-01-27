package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.CampusDTO;

public interface CampusService {
    List<CampusDTO> getAllCampuses();

    CampusDTO getCampusById(Long id);

    CampusDTO createCampus(CampusDTO campusDTO);

    CampusDTO updateCampus(Long id, CampusDTO campusDTO);

    void deleteCampus(Long id);
}
