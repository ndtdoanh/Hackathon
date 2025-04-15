package com.hacof.hackathon.dto;

import com.hacof.hackathon.entity.AuditBase;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
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
