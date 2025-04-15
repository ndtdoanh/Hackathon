package com.hacof.analytics.mapper;

import com.hacof.analytics.dto.response.FeedbackDetailResponse;
import com.hacof.analytics.dto.response.FeedbackResponse;
import com.hacof.analytics.entity.Feedback;
import com.hacof.analytics.entity.FeedbackDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = FeedbackDetailMapper.class)
public interface FeedbackMapper {

    @Mapping(target = "id", expression = "java(String.valueOf(feedback.getId()))")
    @Mapping(target = "hackathonId", source = "hackathon.id")
    @Mapping(target = "mentorId", source = "mentor.id")
    @Mapping(target = "teamId", source = "team.id")
    @Mapping(target = "feedbackDetails", source = "feedbackDetails")
    @Mapping(
            target = "createdByUserName",
            expression = "java(feedback.getCreatedBy() != null ? feedback.getCreatedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    FeedbackResponse toFeedbackResponse(Feedback feedback);

    List<FeedbackResponse> toFeedbackResponses(List<Feedback> feedbacks);

    @Mapping(target = "feedbackId", expression = "java(String.valueOf(feedbackDetail.getFeedback().getId()))")
    FeedbackDetailResponse toFeedbackDetailResponse(FeedbackDetail feedbackDetail);
}
