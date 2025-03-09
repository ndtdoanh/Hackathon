package com.hacof.submission.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.submission.entities.Mentorbooking;

public interface MentorbookingRepository extends JpaRepository<Mentorbooking, Long> {
    List<Mentorbooking> findByMentorId(Long mentorId);

    List<Mentorbooking> findByUserId(Long userId);

    List<Mentorbooking> findByStatus(String status);
}
