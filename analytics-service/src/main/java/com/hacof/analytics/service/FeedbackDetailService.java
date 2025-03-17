package com.hacof.analytics.service;

import java.util.List;

import com.hacof.analytics.dto.request.FeedbackDetailCreateRequest;
import com.hacof.analytics.dto.request.FeedbackDetailUpdateRequest;
import com.hacof.analytics.dto.response.FeedbackDetailResponse;

public interface FeedbackDetailService {
    FeedbackDetailResponse createFeedbackDetail(FeedbackDetailCreateRequest request);

    List<FeedbackDetailResponse> getFeedbackDetails();

    FeedbackDetailResponse getFeedbackDetail(Long id);

    FeedbackDetailResponse updateFeedbackDetail(Long id, FeedbackDetailUpdateRequest request);

    void deleteFeedbackDetail(Long id);

    List<FeedbackDetailResponse> getFeedbackDetailsByFeedbackId(Long feedbackId);

    List<FeedbackDetailResponse> getFeedbackDetailsByMentorId(Long mentorId);

    List<FeedbackDetailResponse> getFeedbackDetailsByHackathonId(Long hackathonId);
}
