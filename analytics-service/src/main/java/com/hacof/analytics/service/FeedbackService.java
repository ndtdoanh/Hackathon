package com.hacof.analytics.service;

import com.hacof.analytics.dto.request.FeedbackRequest;
import com.hacof.analytics.dto.response.FeedbackResponse;

import java.util.List;

public interface FeedbackService {
    FeedbackResponse createFeedback(FeedbackRequest request);

    List<FeedbackResponse> getFeedbacks();

    FeedbackResponse getFeedback(Long id);

    void deleteFeedback(Long id);

    List<FeedbackResponse> getFeedbacksByTeam(Long teamId);

    List<FeedbackResponse> getFeedbacksByHackathon(Long hackathonId);

    List<FeedbackResponse> getFeedbacksByMentor(Long mentorId);
}
