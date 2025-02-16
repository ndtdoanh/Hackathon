package com.hacof.hackathon.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.dto.CampusDTO;
import com.hacof.hackathon.entity.Campus;

public interface CampusService {
    List<CampusDTO> getAllCampuses();

    CampusDTO getCampusById(Long id);

    CampusDTO createCampus(CampusDTO campusDTO);

    CampusDTO updateCampus(Long id, CampusDTO campusDTO);

    void deleteCampus(Long id);

    List<CampusDTO> searchCampuses(Specification<Campus> spec);
}
