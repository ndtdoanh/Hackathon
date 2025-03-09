package com.hacof.submission.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hacof.submission.dtos.request.MentorbookingRequestDTO;
import com.hacof.submission.dtos.response.MentorbookingResponseDTO;
import com.hacof.submission.entities.Mentor;
import com.hacof.submission.entities.Mentorbooking;
import com.hacof.submission.entities.User;
import com.hacof.submission.repositories.MentorRepository;
import com.hacof.submission.repositories.UserRepository;

@Component
public class MentorbookingMapper {

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private UserRepository userRepository;

    public Mentorbooking toEntity(MentorbookingRequestDTO dto) {
        Mentorbooking entity = new Mentorbooking();

        // Tìm Mentor và User từ cơ sở dữ liệu
        Mentor mentor = mentorRepository
                .findById(dto.getMentorId())
                .orElseThrow(() -> new RuntimeException("Mentor not found"));
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        entity.setMentor(mentor);
        entity.setUser(user);
        entity.setBookingDate(dto.getBookingDate());
        return entity;
    }

    // Chuyển từ Mentorbooking Entity sang MentorbookingResponseDTO
    public MentorbookingResponseDTO toResponseDTO(Mentorbooking entity) {
        MentorbookingResponseDTO dto = new MentorbookingResponseDTO();
        dto.setId(entity.getId());
        dto.setMentorId(entity.getMentor().getId());
        dto.setUserId(entity.getUser().getId());
        dto.setBookingDate(entity.getBookingDate());
        dto.setStatus(entity.getStatus().name());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public void updateEntityFromDTO(MentorbookingRequestDTO dto, Mentorbooking entity) {
        if (dto.getBookingDate() != null) {
            entity.setBookingDate(dto.getBookingDate());
        }
    }
}
