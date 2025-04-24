package com.hacof.analytics.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.analytics.entity.FeedbackDetail;

@Repository
public interface FeedbackDetailRepository extends JpaRepository<FeedbackDetail, Long> {
    List<FeedbackDetail> findByFeedbackId(Long feedbackId);

    List<FeedbackDetail> findByFeedback_Mentor_Id(Long mentorId);

    List<FeedbackDetail> findByFeedback_Hackathon_Id(Long hackathonId);

    Optional<FeedbackDetail> findByFeedback_IdAndFeedback_CreatedBy_Id(Long feedbackId, Long createdById);

    boolean existsByFeedbackIdAndCreatedBy_Username(Long feedbackId, String username);

    List<FeedbackDetail> findAllByFeedbackId(Long feedbackId);

    List<FeedbackDetail> findAllByFeedbackIdAndCreatedBy_Username(Long feedbackId, String createdBy);
}
