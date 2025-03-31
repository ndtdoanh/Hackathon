package com.hacof.analytics.dto.request;

import com.hacof.analytics.constant.FeedbackType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackCreateRequest {
    String teamId;
    FeedbackType feedbackType;
    String targetId;
}
