package com.hacof.analytics.service;

import java.util.List;

import com.hacof.analytics.dto.request.FeedbackRequest;
import com.hacof.analytics.dto.response.FeedbackResponse;

public interface FeedbackService {
    FeedbackResponse createFeedback(FeedbackRequest request);

    List<FeedbackResponse> createBulkFeedback(List<FeedbackRequest> requests);

    List<FeedbackResponse> getFeedbacks();

    FeedbackResponse getFeedback(Long id);

    List<FeedbackResponse> getFeedbacksByCreatedByUserName(String username);

    List<FeedbackResponse> getFeedbacksByCreatedByUserNameAndHackathon(String username, Long hackathonId);

    void deleteFeedback(Long id);

    List<FeedbackResponse> getFeedbacksByTeam(Long teamId);

    List<FeedbackResponse> getFeedbacksByHackathon(Long hackathonId);

    List<FeedbackResponse> getFeedbacksByMentor(Long mentorId);
}
