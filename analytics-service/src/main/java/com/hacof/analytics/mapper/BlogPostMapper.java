package com.hacof.analytics.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.analytics.dto.request.BlogPostRequest;
import com.hacof.analytics.dto.response.BlogPostResponse;
import com.hacof.analytics.entity.BlogPost;

@Mapper(componentModel = "spring")
public interface BlogPostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    BlogPost toEntity(BlogPostRequest request);

    BlogPostResponse toResponse(BlogPost blogPost);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntity(@MappingTarget BlogPost blogPost, BlogPostRequest request);
}
