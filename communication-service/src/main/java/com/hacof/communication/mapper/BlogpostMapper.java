package com.hacof.communication.mapper;

import org.springframework.stereotype.Component;

import com.hacof.communication.dto.request.BlogpostRequestDTO;
import com.hacof.communication.dto.response.BlogpostResponseDTO;
import com.hacof.communication.entities.Blogpost;

@Component
public class BlogpostMapper {

    public Blogpost toEntity(BlogpostRequestDTO dto) {
        Blogpost entity = new Blogpost();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        return entity;
    }

    public BlogpostResponseDTO toResponseDTO(Blogpost entity) {
        BlogpostResponseDTO dto = new BlogpostResponseDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setStatus(String.valueOf(entity.getStatus()));
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setUpdatedBy(entity.getUpdatedBy());
        return dto;
    }
}
