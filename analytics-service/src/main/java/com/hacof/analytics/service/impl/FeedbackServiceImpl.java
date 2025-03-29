package com.hacof.analytics.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.analytics.constant.FeedbackType;
import com.hacof.analytics.dto.request.FeedbackCreateRequest;
import com.hacof.analytics.dto.response.FeedbackResponse;
import com.hacof.analytics.entity.Feedback;
import com.hacof.analytics.entity.Hackathon;
import com.hacof.analytics.entity.Team;
import com.hacof.analytics.entity.User;
import com.hacof.analytics.entity.UserRole;
import com.hacof.analytics.exception.AppException;
import com.hacof.analytics.exception.ErrorCode;
import com.hacof.analytics.mapper.FeedbackMapper;
import com.hacof.analytics.repository.FeedbackRepository;
import com.hacof.analytics.repository.HackathonRepository;
import com.hacof.analytics.repository.TeamRepository;
import com.hacof.analytics.repository.UserRepository;
import com.hacof.analytics.service.FeedbackService;
import com.hacof.analytics.util.AuditContext;

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

    @Override
    public FeedbackResponse createFeedback(FeedbackCreateRequest request) {
        User currentUser = AuditContext.getCurrentUser();

        Hackathon hackathon = null;
        User mentor = null;

        if (request.getFeedbackType() == FeedbackType.HACKATHON) {
            hackathon = hackathonRepository
                    .findById(Long.parseLong(request.getTargetId()))
                    .orElseThrow(() -> new AppException(ErrorCode.INVALID_FEEDBACK));
        } else if (request.getFeedbackType() == FeedbackType.MENTOR) {
            mentor = userRepository
                    .findById(Long.parseLong(request.getTargetId()))
                    .orElseThrow(() -> new AppException(ErrorCode.INVALID_FEEDBACK));

            if (mentor.getUserRoles().stream().map(UserRole::getRole).noneMatch(role -> role.getName()
                    .equals("MENTOR"))) {
                throw new AppException(ErrorCode.INVALID_TARGET);
            }
        } else {
            throw new AppException(ErrorCode.INVALID_FEEDBACK_TYPE);
        }

        Team team = teamRepository
                .findById(Long.parseLong(request.getTeamId()))
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_FEEDBACK));

        Feedback feedback = feedbackMapper.toFeedback(request);
        feedback.setCreatedBy(currentUser);
        feedback.setTeam(team);
        feedback.setHackathon(hackathon);
        feedback.setMentor(mentor);

        feedbackRepository.save(feedback);
        return feedbackMapper.toFeedbackResponse(feedback);
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
    public void deleteFeedback(Long id) {
        Feedback feedback =
                feedbackRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_FOUND));

        feedbackRepository.delete(feedback);
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
