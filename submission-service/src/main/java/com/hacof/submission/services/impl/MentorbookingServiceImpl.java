package com.hacof.submission.services.impl;

import com.hacof.submission.dtos.request.MentorbookingRequestDTO;
import com.hacof.submission.dtos.request.StatusRequestDTO;
import com.hacof.submission.dtos.response.MentorbookingResponseDTO;
import com.hacof.submission.entities.Mentorbooking;
import com.hacof.submission.enums.Status;
import com.hacof.submission.repositories.MentorbookingRepository;
import com.hacof.submission.services.MentorbookingService;
import com.hacof.submission.mapper.MentorbookingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MentorbookingServiceImpl implements MentorbookingService {

    @Autowired
    private MentorbookingRepository mentorbookingRepository;

    @Autowired
    private MentorbookingMapper mentorbookingMapper;

    @Override
    public List<MentorbookingResponseDTO> getAllBookings() {
        List<Mentorbooking> mentorBookings = mentorbookingRepository.findAll();
        return mentorBookings.stream()
                .map(mentorbookingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MentorbookingResponseDTO> getBookingsByMentorId(Long mentorId) {
        List<Mentorbooking> mentorBookings = mentorbookingRepository.findByMentorId(mentorId);
        return mentorBookings.stream()
                .map(mentorbookingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MentorbookingResponseDTO> getBookingsByUserId(Long userId) {
        List<Mentorbooking> mentorBookings = mentorbookingRepository.findByUserId(userId);
        return mentorBookings.stream()
                .map(mentorbookingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MentorbookingResponseDTO getBookingById(Long id) {
        Mentorbooking mentorBooking = mentorbookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        return mentorbookingMapper.toResponseDTO(mentorBooking);
    }

    @Override
    public MentorbookingResponseDTO createBooking(MentorbookingRequestDTO requestDTO) {
        Mentorbooking mentorBooking = mentorbookingMapper.toEntity(requestDTO);
        Mentorbooking savedBooking = mentorbookingRepository.save(mentorBooking);
        return mentorbookingMapper.toResponseDTO(savedBooking);
    }

    @Override
    public MentorbookingResponseDTO updateBooking(Long id, MentorbookingRequestDTO requestDTO) {
        Mentorbooking existingBooking = mentorbookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        mentorbookingMapper.updateEntityFromDTO(requestDTO, existingBooking);

        Mentorbooking updatedBooking = mentorbookingRepository.save(existingBooking);
        return mentorbookingMapper.toResponseDTO(updatedBooking);
    }


    @Override
    public void deleteBooking(Long id) {
        Mentorbooking mentorBooking = mentorbookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        mentorbookingRepository.delete(mentorBooking);
    }

    @Override
    public MentorbookingResponseDTO updateBookingStatus(Long id, StatusRequestDTO statusRequestDTO) {
        Mentorbooking existingBooking = mentorbookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        existingBooking.setStatus(Status.valueOf(statusRequestDTO.getStatus()));

        Mentorbooking updatedBooking = mentorbookingRepository.save(existingBooking);

        return mentorbookingMapper.toResponseDTO(updatedBooking);
    }
}
