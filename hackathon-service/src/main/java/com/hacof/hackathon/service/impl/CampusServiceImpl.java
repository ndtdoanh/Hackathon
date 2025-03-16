package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.CampusDTO;
import com.hacof.hackathon.entity.Campus;
import com.hacof.hackathon.exception.InvalidInputException;
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
    public List<CampusDTO> getByAllCriteria(Specification<Campus> spec) {
        List<Campus> campuses = campusRepository.findAll(spec);
        if (campuses.isEmpty()) {
            log.warn("No campus found");
            throw new ResourceNotFoundException("No campus found");
        }
        return campuses.stream().map(campusMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public CampusDTO createCampus(CampusDTO campusDTO) {
        log.info("Creating new campus with name: {}", campusDTO.getName());
        // name is not duplicated
        if (campusRepository.existsByName(campusDTO.getName())) {
            log.warn("Campus creation failed: Name '{}' already exists", campusDTO.getName());
            throw new InvalidInputException("Campus name already exists");
        }

        // create must be in allowed campuses
        List<String> allowedCampuses = List.of("Quy Nhon", "Da Nang", "Can Tho", "TP HCM", "Hoa Lac");
        if (!allowedCampuses.contains(campusDTO.getName())) {
            log.warn("Campus creation failed: Name '{}' is not allowed", campusDTO.getName());
            throw new InvalidInputException(
                    "Campus name is not allowed (Quy Nhon / Da Nang / Can Tho / TP HCM / Hoa Lac)");
        }

        campusDTO.setCreatedDate(LocalDateTime.now());
        campusDTO.setLastModifiedDate(LocalDateTime.now());

        Campus campus = campusMapper.convertToEntity(campusDTO);
        Campus savedCampus = campusRepository.save(campus);
        CampusDTO responseDTO = campusMapper.convertToDTO(savedCampus);

        // set null default if have no data
        if (responseDTO.getEvents() == null) {
            responseDTO.setEvents(new ArrayList<>());
        }
        if (responseDTO.getMentors() == null) {
            responseDTO.setMentors(new ArrayList<>());
        }
        if (responseDTO.getTrainingSessions() == null) {
            responseDTO.setTrainingSessions(new ArrayList<>());
        }
        if (responseDTO.getCreatedBy() == null) {
            responseDTO.setCreatedBy(campusDTO.getCreatedBy());
        }

        if (responseDTO.getLastModifiedBy() == null) {
            responseDTO.setLastModifiedBy(campusDTO.getLastModifiedBy());
        }
        return responseDTO;
    }

    @Override
    public CampusDTO updateCampus(Long id, CampusDTO campusDTO) {
        log.info("Updating campus with id: {}", id);
        if (!campusRepository.existsById(id)) {
            log.warn("Campus update failed: Campus not found with id: {}", id);
            throw new ResourceNotFoundException("Campus not found");
        }

        Campus existingCampus = campusRepository.findById(id).orElseThrow(() -> {
            log.warn("Campus update failed: Campus not found with id: {}", id);
            return new ResourceNotFoundException("Campus not found");
        });

        existingCampus.setName(campusDTO.getName());
        existingCampus.setLocation(campusDTO.getLocation());
        existingCampus.setLastModifiedDate(LocalDateTime.now());

        Campus updatedCampus = campusRepository.save(existingCampus);
        return campusMapper.convertToDTO(updatedCampus);
    }

    @Override
    public void deleteCampus(Long id) {
        log.info("Deleting campus with id: {}", id);
        if (!campusRepository.existsById(id)) {
            log.warn("Campus deletion failed: Campus not found with id: {}", id);
            throw new ResourceNotFoundException("Campus not found");
        }
        campusRepository.deleteById(id);
    }
}
