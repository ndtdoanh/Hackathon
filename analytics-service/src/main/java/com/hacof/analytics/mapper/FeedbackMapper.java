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
    @Mapping(target = "id", expression = "java(String.valueOf(feedback.getId()))")
    @Mapping(target = "teamId", expression = "java(String.valueOf(feedback.getTeam().getId()))")
    @Mapping(
            target = "createdByUserName",
            expression = "java(feedback.getCreatedBy() != null ? feedback.getCreatedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    @Mapping(
            target = "targetId",
            expression = "java(feedback.getHackathon() != null ? String.valueOf(feedback.getHackathon().getId()) : String.valueOf(feedback.getMentor().getId()))"
    )
    FeedbackResponse toFeedbackResponse(Feedback feedback);

    List<FeedbackResponse> toFeedbackResponses(List<Feedback> feedbacks);

    @Mapping(target = "feedbackId", expression = "java(String.valueOf(feedbackDetail.getFeedback().getId()))")
    FeedbackDetailResponse toFeedbackDetailResponse(FeedbackDetail feedbackDetail);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hackathon", ignore = true)
    @Mapping(target = "mentor", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "feedbackDetails", ignore = true)
    Feedback toFeedback(FeedbackCreateRequest request);
}
