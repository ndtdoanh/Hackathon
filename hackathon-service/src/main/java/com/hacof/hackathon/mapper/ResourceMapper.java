package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.ResourceDTO;
import com.hacof.hackathon.entity.Resource;

@Mapper(componentModel = "spring")
public interface ResourceMapper {
    ResourceDTO convertToDTO(Resource resource);

    Resource convertToEntity(ResourceDTO resourceDTO);
}
