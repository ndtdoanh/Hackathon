package com.hacof.analytics.service;

import java.util.List;

import com.hacof.analytics.dto.request.FeedbackDetailRequest;
import com.hacof.analytics.dto.response.FeedbackDetailResponse;

public interface FeedbackDetailService {
    FeedbackDetailResponse createFeedbackDetail(FeedbackDetailRequest request);

    List<FeedbackDetailResponse> createBulkFeedbackDetails(List<FeedbackDetailRequest> requests);

    List<FeedbackDetailResponse> getFeedbackDetails();

    FeedbackDetailResponse getFeedbackDetail(Long id);

    void deleteFeedbackDetail(Long id);

    List<FeedbackDetailResponse> getFeedbackDetailsByFeedbackId(Long feedbackId);

    List<FeedbackDetailResponse> getFeedbackDetailsByMentorId(Long mentorId);

    List<FeedbackDetailResponse> getFeedbackDetailsByHackathonId(Long hackathonId);
}
