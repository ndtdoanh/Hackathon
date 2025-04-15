package com.hacof.analytics.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
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
