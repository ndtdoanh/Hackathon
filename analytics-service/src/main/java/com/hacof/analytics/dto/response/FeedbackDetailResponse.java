package com.hacof.analytics.dto.response;

import java.time.LocalDateTime;

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
public class FeedbackDetailResponse {
    String id;
    String feedbackId;
    String content;
    int maxRating;
    int rate;
    String note;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdByUserName;
}
