package com.hacof.analytics.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.analytics.dto.request.FeedbackDetailRequest;
import com.hacof.analytics.dto.response.FeedbackDetailResponse;
import com.hacof.analytics.entity.Feedback;
import com.hacof.analytics.entity.FeedbackDetail;
import com.hacof.analytics.exception.AppException;
import com.hacof.analytics.exception.ErrorCode;
import com.hacof.analytics.mapper.FeedbackDetailMapper;
import com.hacof.analytics.repository.FeedbackDetailRepository;
import com.hacof.analytics.repository.FeedbackRepository;
import com.hacof.analytics.service.FeedbackDetailService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeedbackDetailServiceImpl implements FeedbackDetailService {
    FeedbackDetailRepository feedbackDetailRepository;
    FeedbackRepository feedbackRepository;
    FeedbackDetailMapper feedbackDetailMapper;

    @Override
    public FeedbackDetailResponse createFeedbackDetail(FeedbackDetailRequest request) {
        Feedback feedback = feedbackRepository
                .findById(Long.parseLong(request.getFeedbackId()))
                .orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_FOUND));

        FeedbackDetail feedbackDetail = feedbackDetailMapper.toFeedbackDetail(request);

        feedbackDetail.setFeedback(feedback);

        FeedbackDetail saved = feedbackDetailRepository.save(feedbackDetail);

        return feedbackDetailMapper.toFeedbackDetailResponse(saved);
    }

    @Override
    public List<FeedbackDetailResponse> getFeedbackDetails() {
        return feedbackDetailMapper.toFeedbackDetailResponseList(feedbackDetailRepository.findAll());
    }

    @Override
    public FeedbackDetailResponse getFeedbackDetail(Long id) {
        FeedbackDetail feedbackDetail = feedbackDetailRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_DETAIL_NOT_FOUND));
        return feedbackDetailMapper.toFeedbackDetailResponse(feedbackDetail);
    }

    @Override
    public void deleteFeedbackDetail(Long id) {
        FeedbackDetail feedbackDetail = feedbackDetailRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_DETAIL_NOT_FOUND));

        feedbackDetailRepository.delete(feedbackDetail);
    }

    @Override
    public List<FeedbackDetailResponse> getFeedbackDetailsByFeedbackId(Long feedbackId) {
        return feedbackDetailMapper.toFeedbackDetailResponseList(feedbackDetailRepository.findByFeedbackId(feedbackId));
    }

    @Override
    public List<FeedbackDetailResponse> getFeedbackDetailsByMentorId(Long mentorId) {
        return feedbackDetailMapper.toFeedbackDetailResponseList(
                feedbackDetailRepository.findByFeedback_Mentor_Id(mentorId));
    }

    @Override
    public List<FeedbackDetailResponse> getFeedbackDetailsByHackathonId(Long hackathonId) {
        return feedbackDetailMapper.toFeedbackDetailResponseList(
                feedbackDetailRepository.findByFeedback_Hackathon_Id(hackathonId));
    }
}
