package com.hacof.submission.services;

import com.hacof.submission.dtos.request.MentorbookingRequestDTO;
import com.hacof.submission.dtos.request.StatusRequestDTO;
import com.hacof.submission.dtos.response.MentorbookingResponseDTO;
import java.util.List;

public interface MentorbookingService {

    List<MentorbookingResponseDTO> getAllBookings();

    List<MentorbookingResponseDTO> getBookingsByMentorId(Long mentorId);

    List<MentorbookingResponseDTO> getBookingsByUserId(Long userId);

    MentorbookingResponseDTO getBookingById(Long id);

    MentorbookingResponseDTO createBooking(MentorbookingRequestDTO requestDTO);

    MentorbookingResponseDTO updateBooking(Long id, MentorbookingRequestDTO requestDTO);

    void deleteBooking(Long id);

    MentorbookingResponseDTO updateBookingStatus(Long id, StatusRequestDTO statusRequestDTO);

}
