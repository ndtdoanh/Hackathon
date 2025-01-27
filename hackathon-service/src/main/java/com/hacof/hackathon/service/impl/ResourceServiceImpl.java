package com.hacof.hackathon.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.ResourceDTO;
import com.hacof.hackathon.entity.Resource;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.ResourceMapper;
import com.hacof.hackathon.repository.ResourceRepository;
import com.hacof.hackathon.service.ResourceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;

    @Override
    public ResourceDTO allocateResource(ResourceDTO resourceDTO) {
        Resource resource = resourceMapper.convertToEntity(resourceDTO);
        Resource savedResource = resourceRepository.save(resource);
        return resourceMapper.convertToDTO(savedResource);
    }

    @Override
    public List<ResourceDTO> getAllResources() {
        return resourceRepository.findAll().stream()
                .map(resourceMapper::convertToDTO)
                .toList();
    }

    @Override
    public ResourceDTO getResourceById(Long id) {
        return resourceRepository
                .findById(id)
                .map(resourceMapper::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    @Override
    public ResourceDTO updateResource(Long id, ResourceDTO resourceDTO) {
        Resource existingResource =
                resourceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        existingResource.setName(resourceDTO.getName());

        // Directly set the enum values
        existingResource.setResourceType(resourceDTO.getResourceType());
        existingResource.setStatus(resourceDTO.getStatus());

        Resource updatedResource = resourceRepository.save(existingResource);
        return resourceMapper.convertToDTO(updatedResource);
    }

    @Override
    public void deleteResource(Long id) {
        Resource resource =
                resourceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        resourceRepository.delete(resource);
    }
}
