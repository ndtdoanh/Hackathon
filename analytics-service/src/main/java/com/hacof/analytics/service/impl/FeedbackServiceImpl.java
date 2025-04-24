package com.hacof.analytics.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hacof.analytics.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hacof.analytics.dto.request.FeedbackRequest;
import com.hacof.analytics.dto.response.FeedbackResponse;
import com.hacof.analytics.entity.Feedback;
import com.hacof.analytics.entity.Hackathon;
import com.hacof.analytics.exception.AppException;
import com.hacof.analytics.exception.ErrorCode;
import com.hacof.analytics.mapper.FeedbackDetailMapper;
import com.hacof.analytics.mapper.FeedbackMapper;
import com.hacof.analytics.repository.FeedbackDetailRepository;
import com.hacof.analytics.repository.FeedbackRepository;
import com.hacof.analytics.repository.HackathonRepository;
import com.hacof.analytics.repository.TeamRepository;
import com.hacof.analytics.repository.UserRepository;
import com.hacof.analytics.service.FeedbackService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeedbackServiceImpl implements FeedbackService {
    FeedbackRepository feedbackRepository;
    FeedbackMapper feedbackMapper;
    UserRepository userRepository;
    HackathonRepository hackathonRepository;
    TeamRepository teamRepository;
    FeedbackDetailMapper feedbackDetailMapper;
    FeedbackDetailRepository feedbackDetailRepository;

    @Override
    public FeedbackResponse createFeedback(FeedbackRequest request) {
        boolean hasHackathon = StringUtils.hasText(request.getHackathonId());
        boolean hasMentor = StringUtils.hasText(request.getMentorId());

        if (!hasHackathon && !hasMentor) {
            throw new AppException(ErrorCode.FEEDBACK_TARGET_REQUIRED);
        }

        Feedback feedback = new Feedback();

        if (hasHackathon) {
            Long hackathonId = Long.parseLong(request.getHackathonId());
            Hackathon hackathon = hackathonRepository.findById(hackathonId)
                    .orElseThrow(() -> new AppException(ErrorCode.HACKATHON_NOT_FOUND));

            if (feedbackRepository.existsByHackathonId(hackathonId)) {
                throw new AppException(ErrorCode.FEEDBACK_EXISTED);
            }

            feedback.setHackathon(hackathon);
        }

        if (hasMentor) {
            Long mentorId = Long.parseLong(request.getMentorId());
            User mentor = userRepository.findById(mentorId)
                    .orElseThrow(() -> new AppException(ErrorCode.MENTOR_NOT_FOUND));

            if (feedbackRepository.existsByMentorId(mentorId)) {
                throw new AppException(ErrorCode.FEEDBACK_EXISTED);
            }

            feedback.setMentor(mentor);
        }

        Feedback saved = feedbackRepository.save(feedback);
        return feedbackMapper.toFeedbackResponse(saved);
    }

    @Override
    public List<FeedbackResponse> createBulkFeedback(List<FeedbackRequest> requests) {
        List<Feedback> feedbacksToSave = new ArrayList<>();

        for (FeedbackRequest request : requests) {
            if (!StringUtils.hasText(request.getHackathonId())) {
                throw new AppException(ErrorCode.HACKATHON_REQUIRED);
            }

            Long hackathonId = Long.parseLong(request.getHackathonId());

            if (feedbackRepository.existsByHackathonId(hackathonId)) {
                throw new AppException(ErrorCode.FEEDBACK_EXISTED);
            }

            Hackathon hackathon = hackathonRepository
                    .findById(hackathonId)
                    .orElseThrow(() -> new AppException(ErrorCode.HACKATHON_NOT_FOUND));

            Feedback feedback = new Feedback();
            feedback.setHackathon(hackathon);

            feedbacksToSave.add(feedback);
        }

        List<Feedback> saved = feedbackRepository.saveAll(feedbacksToSave);
        return feedbackMapper.toFeedbackResponses(saved);
    }

    @Override
    public List<FeedbackResponse> getFeedbacks() {
        return feedbackMapper.toFeedbackResponses(feedbackRepository.findAll());
    }

    @Override
    public FeedbackResponse getFeedback(Long id) {
        return feedbackRepository
                .findById(id)
                .map(feedbackMapper::toFeedbackResponse)
                .orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_FOUND));
    }

    @Override
    public FeedbackResponse getFeedback(String hackathonIdStr, String mentorIdStr) {
        boolean hasHackathon = StringUtils.hasText(hackathonIdStr);
        boolean hasMentor = StringUtils.hasText(mentorIdStr);

        if (!hasHackathon && !hasMentor) {
            throw new AppException(ErrorCode.FEEDBACK_TARGET_REQUIRED);
        }

        Optional<Feedback> feedbackOptional;

        if (hasHackathon && hasMentor) {
            Long hackathonId = Long.parseLong(hackathonIdStr);
            Long mentorId = Long.parseLong(mentorIdStr);
            feedbackOptional = feedbackRepository.findByHackathonIdAndMentorId(hackathonId, mentorId);
        } else if (hasHackathon) {
            Long hackathonId = Long.parseLong(hackathonIdStr);
            feedbackOptional = feedbackRepository.findByHackathon_Id(hackathonId);
        } else {
            Long mentorId = Long.parseLong(mentorIdStr);
            feedbackOptional = feedbackRepository.findByMentor_Id(mentorId);
        }

        Feedback feedback = feedbackOptional
                .orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_FOUND));

        return feedbackMapper.toFeedbackResponse(feedback);
    }

    @Override
    public void deleteFeedback(Long id) {
        Feedback feedback =
                feedbackRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_FOUND));

        feedbackRepository.delete(feedback);
    }

    @Override
    public List<FeedbackResponse> getFeedbacksByCreatedByUserName(String username) {
        List<Feedback> feedbacks = feedbackRepository.findByCreatedByUsername(username);
        return feedbackMapper.toFeedbackResponses(feedbacks);
    }

    @Override
    public List<FeedbackResponse> getFeedbacksByCreatedByUserNameAndHackathon(String username, Long hackathonId) {
        List<Feedback> feedbacks = feedbackRepository.findByCreatedByUsernameAndHackathonId(username, hackathonId);
        return feedbackMapper.toFeedbackResponses(feedbacks);
    }

    @Override
    public List<FeedbackResponse> getFeedbacksByTeam(Long teamId) {
        teamRepository.findById(teamId).orElseThrow(() -> new AppException(ErrorCode.TEAM_NOT_FOUND));

        return feedbackMapper.toFeedbackResponses(feedbackRepository.findByTeamId(teamId));
    }

    @Override
    public List<FeedbackResponse> getFeedbacksByHackathon(Long hackathonId) {
        hackathonRepository.findById(hackathonId).orElseThrow(() -> new AppException(ErrorCode.HACKATHON_NOT_FOUND));

        return feedbackMapper.toFeedbackResponses(feedbackRepository.findByHackathonId(hackathonId));
    }

    @Override
    public List<FeedbackResponse> getFeedbacksByMentor(Long mentorId) {
        userRepository.findById(mentorId).orElseThrow(() -> new AppException(ErrorCode.MENTOR_NOT_FOUND));

        return feedbackMapper.toFeedbackResponses(feedbackRepository.findByMentorId(mentorId));
    }
}
