package com.hacof.hackathon.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.CampusDTO;
import com.hacof.hackathon.entity.Campus;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.CampusMapper;
import com.hacof.hackathon.repository.CampusRepository;
import com.hacof.hackathon.service.CampusService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CampusServiceImpl implements CampusService {
    private final CampusMapper campusMapper;
    private final CampusRepository campusRepository;

    @Override
    public List<CampusDTO> getAllCampuses() {
        log.info("Fetching all campuses");
        return campusRepository.findAll().stream()
                .map(campusMapper::convertToDTO)
                .toList();
    }

    @Override
    public CampusDTO getCampusById(Long id) {
        log.info("Fetching campus with id: {}", id);
        return campusRepository
                .findById(id)
                .map(campusMapper::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Campus not found"));
    }

    @Override
    public CampusDTO createCampus(CampusDTO campusDTO) {
        log.info("Creating new campus with name: {}", campusDTO.getName());
        Campus campus = campusMapper.convertToEntity(campusDTO);
        Campus savedCampus = campusRepository.save(campus);
        return campusMapper.convertToDTO(savedCampus);
    }

    @Override
    public CampusDTO updateCampus(Long id, CampusDTO campusDTO) {
        log.info("Updating campus with id: {}", id);
        Campus existingCampus =
                campusRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Campus not found"));
        existingCampus.setName(campusDTO.getName());
        existingCampus.setLocation(campusDTO.getLocation());
        Campus updatedCampus = campusRepository.save(existingCampus);
        return campusMapper.convertToDTO(updatedCampus);
    }

    @Override
    public void deleteCampus(Long id) {
        log.info("Deleting campus with id: {}", id);
        if (!campusRepository.existsById(id)) {
            throw new ResourceNotFoundException("Campus not found");
        }
        campusRepository.deleteById(id);
    }
}
