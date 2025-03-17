package com.hacof.analytics.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.analytics.dto.request.FeedbackCreateRequest;
import com.hacof.analytics.dto.response.FeedbackDetailResponse;
import com.hacof.analytics.dto.response.FeedbackResponse;
import com.hacof.analytics.entity.Feedback;
import com.hacof.analytics.entity.FeedbackDetail;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    @Mapping(target = "teamId", source = "team.id")
    @Mapping(
            target = "targetId",
            expression =
                    "java(feedback.getHackathon() != null ? feedback.getHackathon().getId() : feedback.getMentor().getId())")
    FeedbackResponse toFeedbackResponse(Feedback feedback);

    List<FeedbackResponse> toFeedbackResponses(List<Feedback> feedbacks);

    FeedbackDetailResponse toFeedbackDetailResponse(FeedbackDetail feedbackDetail);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hackathon", ignore = true)
    @Mapping(target = "mentor", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "feedbackDetails", ignore = true)
    Feedback toFeedback(FeedbackCreateRequest request);
}
