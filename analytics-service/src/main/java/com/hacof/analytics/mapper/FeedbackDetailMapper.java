package com.hacof.analytics.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.hacof.analytics.dto.request.FeedbackDetailCreateRequest;
import com.hacof.analytics.dto.response.FeedbackDetailResponse;
import com.hacof.analytics.entity.Feedback;
import com.hacof.analytics.entity.FeedbackDetail;

@Mapper(componentModel = "spring")
public interface FeedbackDetailMapper {
    FeedbackDetail toFeedbackDetail(FeedbackDetailCreateRequest request, Feedback feedback);

    FeedbackDetailResponse toFeedbackDetailResponse(FeedbackDetail feedbackDetail);

    List<FeedbackDetailResponse> toFeedbackDetailResponseList(List<FeedbackDetail> feedbackDetails);
}
