package com.hacof.analytics.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.hacof.analytics.dto.request.FeedbackDetailCreateRequest;
import com.hacof.analytics.dto.response.FeedbackDetailResponse;
import com.hacof.analytics.entity.Feedback;
import com.hacof.analytics.entity.FeedbackDetail;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedbackDetailMapper {
    FeedbackDetail toFeedbackDetail(FeedbackDetailCreateRequest request, Feedback feedback);

    @Mapping(target = "id", expression = "java(String.valueOf(feedbackDetail.getId()))")
    @Mapping(target = "feedbackId", expression = "java(String.valueOf(feedbackDetail.getFeedback().getId()))")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    FeedbackDetailResponse toFeedbackDetailResponse(FeedbackDetail feedbackDetail);

    List<FeedbackDetailResponse> toFeedbackDetailResponseList(List<FeedbackDetail> feedbackDetails);
}
