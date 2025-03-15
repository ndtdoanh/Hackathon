package com.hacof.submission.services.impl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.submission.dtos.request.MentorbookingRequestDTO;
import com.hacof.submission.dtos.request.StatusRequestDTO;
import com.hacof.submission.dtos.response.MentorbookingResponseDTO;
import com.hacof.submission.entities.Mentor;
import com.hacof.submission.entities.Mentorbooking;
import com.hacof.submission.entities.User;
import com.hacof.submission.enums.Status;
import com.hacof.submission.mapper.MentorbookingMapper;
import com.hacof.submission.repositories.MentorRepository;
import com.hacof.submission.repositories.MentorbookingRepository;
import com.hacof.submission.repositories.UserRepository;
import com.hacof.submission.services.MentorbookingService;

@Service
public class MentorbookingServiceImpl implements MentorbookingService {

    @Autowired
    private MentorbookingRepository mentorbookingRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MentorbookingMapper mentorbookingMapper;

    @Override
    public List<MentorbookingResponseDTO> getAllBookings() {
        List<Mentorbooking> mentorBookings = mentorbookingRepository.findAll();
        return mentorBookings.stream().map(mentorbookingMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<MentorbookingResponseDTO> getBookingsByMentorId(Long mentorId) {
        List<Mentorbooking> mentorBookings = mentorbookingRepository.findByMentorId(mentorId);
        if (mentorBookings.isEmpty()) {
            throw new RuntimeException("No bookings found for mentor with ID: " + mentorId);
        }
        return mentorBookings.stream().map(mentorbookingMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<MentorbookingResponseDTO> getBookingsByUserId(Long userId) {
        List<Mentorbooking> mentorBookings = mentorbookingRepository.findByUserId(userId);
        if (mentorBookings.isEmpty()) {
            throw new RuntimeException("No bookings found for user with ID: " + userId);
        }
        return mentorBookings.stream().map(mentorbookingMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public MentorbookingResponseDTO getBookingById(Long id) {
        Mentorbooking mentorBooking = mentorbookingRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
        return mentorbookingMapper.toResponseDTO(mentorBooking);
    }

    @Override
    public MentorbookingResponseDTO createBooking(MentorbookingRequestDTO requestDTO) {
        Mentor mentor = mentorRepository
                .findById(requestDTO.getMentorId())
                .orElseThrow(() -> new RuntimeException("Mentor not found with ID: " + requestDTO.getMentorId()));
        User user = userRepository
                .findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + requestDTO.getUserId()));
        if (requestDTO.getBookingDate().isBefore(Instant.now())) {
            throw new RuntimeException("Booking date cannot be in the past.");
        }
        Status status = Status.valueOf("PENDING");

        Mentorbooking mentorBooking = mentorbookingMapper.toEntity(requestDTO);
        mentorBooking.setMentor(mentor);
        mentorBooking.setUser(user);
        mentorBooking.setStatus(status);

        Mentorbooking savedBooking = mentorbookingRepository.save(mentorBooking);
        return mentorbookingMapper.toResponseDTO(savedBooking);
    }

    @Override
    public MentorbookingResponseDTO updateBooking(Long id, MentorbookingRequestDTO requestDTO) {
        Mentorbooking existingBooking = mentorbookingRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
        if (requestDTO.getBookingDate().isBefore(Instant.now())) {
            throw new RuntimeException("Booking date cannot be in the past.");
        }
        mentorbookingMapper.updateEntityFromDTO(requestDTO, existingBooking);

        Mentorbooking updatedBooking = mentorbookingRepository.save(existingBooking);
        return mentorbookingMapper.toResponseDTO(updatedBooking);
    }

    @Override
    public void deleteBooking(Long id) {
        Mentorbooking mentorBooking =
                mentorbookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found!"));
        mentorbookingRepository.delete(mentorBooking);
    }

    @Override
    public MentorbookingResponseDTO updateBookingStatus(Long id, StatusRequestDTO statusRequestDTO) {
        Mentorbooking existingBooking =
                mentorbookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found!"));
        existingBooking.setStatus(Status.valueOf(statusRequestDTO.getStatus()));

        Mentorbooking updatedBooking = mentorbookingRepository.save(existingBooking);

        return mentorbookingMapper.toResponseDTO(updatedBooking);
    }
}
