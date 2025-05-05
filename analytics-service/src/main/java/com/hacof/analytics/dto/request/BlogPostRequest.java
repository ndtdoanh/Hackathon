package com.hacof.analytics.dto.request;

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
public class BlogPostRequest {
    String title;
    String slug;
    String bannerImageUrl;
    String content;
    BlogPostStatus status;
    int wordCount;
}
