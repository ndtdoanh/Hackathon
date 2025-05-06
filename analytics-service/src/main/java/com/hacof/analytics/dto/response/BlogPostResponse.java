package com.hacof.analytics.dto.response;

import java.time.LocalDateTime;

import com.hacof.analytics.constant.BlogPostStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogPostResponse {
    String id;
    String title;
    String slug;
    String bannerImageUrl;
    String content;
    int wordCount;
    BlogPostStatus status;
    LocalDateTime publishedAt;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdByUserName;
}
