package com.hacof.analytics.dto.response;

import java.time.LocalDateTime;
import java.util.List;

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
    String teamId;
    String hackathonId;
    String mentorId;
    List<FeedbackDetailResponse> feedbackDetails;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdByUserName;
}
