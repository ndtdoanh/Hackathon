package com.hacof.analytics.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.hacof.analytics.constant.FeedbackType;

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
public class FeedbackResponse {
    String id;
    String targetId;
    String teamId;
    FeedbackType feedbackType;
    List<FeedbackDetailResponse> feedbackDetails;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
    String createdByUserId;
}
