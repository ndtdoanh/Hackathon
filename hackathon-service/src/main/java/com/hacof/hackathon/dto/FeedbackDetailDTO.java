package com.hacof.hackathon.dto;

import com.hacof.hackathon.entity.AuditBase;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackDetailDTO extends AuditBase {
    String id;
    FeedbackDTO feedback;
    String feedbackId;
    String content;
    int maxRating;
    int rate;
    String note;
}
