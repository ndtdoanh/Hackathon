package com.hacof.analytics.dto.response;

import java.time.LocalDateTime;

import com.hacof.analytics.constant.BlogPostStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogPostResponse {
    String id;
    String title;
    String slug;
    String content;
    BlogPostStatus status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdByUserName;
}
