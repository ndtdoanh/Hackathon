package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.ResourceDTO;
import com.hacof.hackathon.entity.CompetitionRound;
import com.hacof.hackathon.entity.Resource;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.CompetitionRoundMapper;
import com.hacof.hackathon.mapper.ResourceMapper;
import com.hacof.hackathon.repository.CompetitionRoundRepository;
import com.hacof.hackathon.repository.ResourceRepository;
import com.hacof.hackathon.service.ResourceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final CompetitionRoundRepository roundRepository;
    private final ResourceMapper resourceMapper;
    private final CompetitionRoundMapper roundMapper;

    @Override
    public List<ResourceDTO> getAllResources() {
        if (resourceRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No resources found");
        }
        return resourceRepository.findAll().stream()
                .map(resourceMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResourceDTO getResourceById(Long id) {
        return resourceRepository
                .findById(id)
                .map(resourceMapper::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
    }

    @Override
    public ResourceDTO createResource(ResourceDTO resourceDTO) {
        CompetitionRound round = roundRepository
                .findById(resourceDTO.getCompetitionRoundId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Round not found with id: " + resourceDTO.getCompetitionRoundId()));

        Resource resource = resourceMapper.convertToEntity(resourceDTO);
        resource.setCompetitionRound(round);
        Resource savedResource = resourceRepository.save(resource);
        ResourceDTO savedResourceDTO = resourceMapper.convertToDTO(savedResource);
        savedResourceDTO.setCompetitionRoundId(round.getId());
        return savedResourceDTO;
    }

    @Override
    public ResourceDTO updateResource(Long id, ResourceDTO resourceDTO) {
        Resource existingResource = resourceRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
        existingResource.setName(resourceDTO.getName());
        existingResource.setResourceType(resourceDTO.getResourceType());
        existingResource.setStatus(resourceDTO.getStatus());
        existingResource.setQuantity(resourceDTO.getQuantity());
        Resource updatedResource = resourceRepository.save(existingResource);
        return resourceMapper.convertToDTO(updatedResource);
    }

    @Override
    public void deleteResource(Long id) {
        Resource resource = resourceRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
        resourceRepository.delete(resource);
    }

    @Override
    public List<ResourceDTO> getResourcesByRoundId(Long roundId) {
        CompetitionRound round = roundRepository
                .findById(roundId)
                .orElseThrow(() -> new ResourceNotFoundException("Round not found with id: " + roundId));
        return round.getResources().stream().map(resourceMapper::convertToDTO).collect(Collectors.toList());
    }
}
