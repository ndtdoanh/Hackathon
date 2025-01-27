package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.ResourceDTO;

public interface ResourceService {
    ResourceDTO allocateResource(ResourceDTO resourceDTO);

    List<ResourceDTO> getAllResources();

    ResourceDTO getResourceById(Long id);

    ResourceDTO updateResource(Long id, ResourceDTO resourceDTO);

    void deleteResource(Long id);
}
