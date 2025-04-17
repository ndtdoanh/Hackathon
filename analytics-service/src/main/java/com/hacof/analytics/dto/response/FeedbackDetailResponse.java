package com.hacof.analytics.dto.response;

import java.time.LocalDateTime;

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
