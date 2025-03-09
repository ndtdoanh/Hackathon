package com.hacof.communication.mapper;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.hacof.communication.dto.request.BlogcommentRequestDTO;
import com.hacof.communication.dto.response.BlogcommentResponseDTO;
import com.hacof.communication.entities.Blogcomment;

@Component
public class BlogcommentMapper {

    // Chuyển đổi từ BlogcommentRequestDTO thành Blogcomment entity
    public Blogcomment toEntity(BlogcommentRequestDTO dto) {
        Blogcomment entity = new Blogcomment();
        entity.setComment(dto.getComment());
        return entity;
    }

    // Chuyển đổi từ Blogcomment entity thành BlogcommentResponseDTO
    public BlogcommentResponseDTO toResponseDTO(Blogcomment entity) {
        BlogcommentResponseDTO dto = new BlogcommentResponseDTO();
        dto.setId(entity.getId());
        dto.setPostId(entity.getPost() != null ? entity.getPost().getId() : null);
        dto.setUserId(entity.getUser() != null ? entity.getUser().getId() : null);
        dto.setComment(entity.getComment());
        dto.setStatus(entity.getStatus() != null ? entity.getStatus().toString() : null);
        dto.setCreatedBy(entity.getCreatedBy());

        // Sử dụng toString() để chuyển đổi Instant thành String
        dto.setCreatedAt(formatInstant(entity.getCreatedAt()));
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedAt(formatInstant(entity.getUpdatedAt()));

        return dto;
    }

    // Phương thức chuyển đổi Instant thành String với định dạng
    private String formatInstant(Instant instant) {
        return instant != null ? DateTimeFormatter.ISO_INSTANT.format(instant) : null;
    }
}
