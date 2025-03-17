package com.hacof.analytics.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.analytics.dto.request.FeedbackDetailCreateRequest;
import com.hacof.analytics.dto.request.FeedbackDetailUpdateRequest;
import com.hacof.analytics.dto.response.FeedbackDetailResponse;
import com.hacof.analytics.entity.Feedback;
import com.hacof.analytics.entity.FeedbackDetail;
import com.hacof.analytics.entity.User;
import com.hacof.analytics.exception.AppException;
import com.hacof.analytics.exception.ErrorCode;
import com.hacof.analytics.mapper.FeedbackDetailMapper;
import com.hacof.analytics.repository.FeedbackDetailRepository;
import com.hacof.analytics.repository.FeedbackRepository;
import com.hacof.analytics.service.FeedbackDetailService;
import com.hacof.analytics.util.AuditContext;

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
    public FeedbackDetailResponse createFeedbackDetail(FeedbackDetailCreateRequest request) {
        Feedback feedback = feedbackRepository
                .findById(request.getFeedbackId())
                .orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_FOUND));

        User currentUser = AuditContext.getCurrentUser();

        feedbackDetailRepository
                .findByFeedback_IdAndFeedback_CreatedBy_Id(request.getFeedbackId(), currentUser.getId())
                .ifPresent(feedbackDetail -> {
                    throw new AppException(ErrorCode.FEEDBACK_DETAIL_ALREADY_EXISTS);
                });

        FeedbackDetail feedbackDetail =
                new FeedbackDetail(feedback, request.getContent(), 10, request.getRate(), request.getNote());

        feedbackDetail = feedbackDetailRepository.save(feedbackDetail);
        return feedbackDetailMapper.toFeedbackDetailResponse(feedbackDetail);
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
    public FeedbackDetailResponse updateFeedbackDetail(Long id, FeedbackDetailUpdateRequest request) {
        FeedbackDetail feedbackDetail = feedbackDetailRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_DETAIL_NOT_FOUND));

        feedbackDetail.setContent(request.getContent());
        feedbackDetail.setRate(request.getRate());
        feedbackDetail.setNote(request.getNote());
        feedbackDetail = feedbackDetailRepository.save(feedbackDetail);

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
