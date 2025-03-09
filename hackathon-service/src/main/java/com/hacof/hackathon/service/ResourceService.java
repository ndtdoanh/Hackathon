package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.ResourceDTO;

public interface ResourceService {
    List<ResourceDTO> getAllResources();

    ResourceDTO getResourceById(Long id);

    ResourceDTO createResource(ResourceDTO resourceDTO);

    ResourceDTO updateResource(Long id, ResourceDTO resourceDTO);

    void deleteResource(Long id);

    List<ResourceDTO> getResourcesByRoundId(Long roundId);
}
