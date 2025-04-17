package com.hacof.analytics.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.analytics.dto.request.FeedbackDetailRequest;
import com.hacof.analytics.dto.response.FeedbackDetailResponse;
import com.hacof.analytics.entity.FeedbackDetail;

@Mapper(componentModel = "spring")
public interface FeedbackDetailMapper {

    @Mapping(target = "feedback.id", source = "feedbackId")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "maxRating", source = "maxRating")
    @Mapping(target = "rate", source = "rate")
    @Mapping(target = "note", source = "note")
    FeedbackDetail toFeedbackDetail(FeedbackDetailRequest request);

    @Mapping(target = "id", expression = "java(String.valueOf(feedbackDetail.getId()))")
    @Mapping(target = "feedbackId", source = "feedback.id")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "maxRating", source = "maxRating")
    @Mapping(target = "rate", source = "rate")
    @Mapping(target = "note", source = "note")
    @Mapping(
            target = "createdByUserName",
            expression =
                    "java(feedbackDetail.getCreatedBy() != null ? feedbackDetail.getCreatedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    FeedbackDetailResponse toFeedbackDetailResponse(FeedbackDetail feedbackDetail);

    List<FeedbackDetailResponse> toFeedbackDetailResponseList(List<FeedbackDetail> feedbackDetails);
}
