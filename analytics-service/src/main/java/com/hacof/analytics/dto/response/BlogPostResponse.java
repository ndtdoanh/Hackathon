package com.hacof.analytics.dto.response;

import com.hacof.analytics.constant.BlogPostStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
    String createdByUserId;
}
