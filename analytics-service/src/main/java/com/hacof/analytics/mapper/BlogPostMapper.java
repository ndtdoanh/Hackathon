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

    @Mapping(target = "id", expression = "java(String.valueOf(blogPost.getId()))")
    @Mapping(
            target = "createdByUserName",
            expression = "java(blogPost.getCreatedBy() != null ? blogPost.getCreatedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    BlogPostResponse toResponse(BlogPost blogPost);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntity(@MappingTarget BlogPost blogPost, BlogPostRequest request);
}
