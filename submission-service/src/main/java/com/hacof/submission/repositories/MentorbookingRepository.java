package com.hacof.submission.repositories;

import com.hacof.submission.entities.Mentorbooking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MentorbookingRepository extends JpaRepository<Mentorbooking, Long> {
    List<Mentorbooking> findByMentorId(Long mentorId);
    List<Mentorbooking> findByUserId(Long userId);
    List<Mentorbooking> findByStatus(String status);
}
