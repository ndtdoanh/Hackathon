package com.hacof.analytics.dto.response;

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
    Long id;
    Long targetId;
    Long teamId;
    FeedbackType feedbackType;
    List<FeedbackDetailResponse> feedbackDetails;
}
