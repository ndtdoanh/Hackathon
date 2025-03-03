package com.hacof.submission.controllers;

import com.hacof.submission.dtos.request.MentorbookingRequestDTO;
import com.hacof.submission.dtos.request.StatusRequestDTO;
import com.hacof.submission.dtos.response.MentorbookingResponseDTO;
import com.hacof.submission.responses.CommonResponse;
import com.hacof.submission.services.MentorbookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mentorbookings")
public class MentorbookingController {

    @Autowired
    private MentorbookingService mentorbookingService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<MentorbookingResponseDTO>>> getAllBookings() {
        CommonResponse<List<MentorbookingResponseDTO>> response = new CommonResponse<>();
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all mentor bookings successfully!");
            response.setData(mentorbookingService.getAllBookings());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/byMentorId/{mentorId}")
    public ResponseEntity<CommonResponse<List<MentorbookingResponseDTO>>> getBookingsByMentorId(@PathVariable Long mentorId) {
        CommonResponse<List<MentorbookingResponseDTO>> response = new CommonResponse<>();
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all mentor bookings by mentorId successfully!");
            response.setData(mentorbookingService.getBookingsByMentorId(mentorId));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<CommonResponse<List<MentorbookingResponseDTO>>> getBookingsByUserId(@PathVariable Long userId) {
        CommonResponse<List<MentorbookingResponseDTO>> response = new CommonResponse<>();
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all mentor bookings by userId successfully!");
            response.setData(mentorbookingService.getBookingsByUserId(userId));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<MentorbookingResponseDTO>> getBookingById(@PathVariable Long id) {
        CommonResponse<MentorbookingResponseDTO> response = new CommonResponse<>();
        try {
            MentorbookingResponseDTO booking = mentorbookingService.getBookingById(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched mentor booking by id successfully!");
            response.setData(booking);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<CommonResponse<MentorbookingResponseDTO>> createBooking(@RequestBody MentorbookingRequestDTO requestDTO) {
        CommonResponse<MentorbookingResponseDTO> response = new CommonResponse<>();
        try {
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Mentor booking created successfully!");
            response.setData(mentorbookingService.createBooking(requestDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<MentorbookingResponseDTO>> updateBooking(@PathVariable Long id, @RequestBody MentorbookingRequestDTO requestDTO) {
        CommonResponse<MentorbookingResponseDTO> response = new CommonResponse<>();
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Mentor booking updated successfully!");
            response.setData(mentorbookingService.updateBooking(id, requestDTO));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteBooking(@PathVariable Long id) {
        CommonResponse<Void> response = new CommonResponse<>();
        try {
            mentorbookingService.deleteBooking(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Mentor booking deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<CommonResponse<MentorbookingResponseDTO>> updateBookingStatus(@PathVariable Long id,
                                                                                        @RequestBody StatusRequestDTO statusRequestDTO) {
        CommonResponse<MentorbookingResponseDTO> response = new CommonResponse<>();
        try {
            MentorbookingResponseDTO updatedBooking = mentorbookingService.updateBookingStatus(id, statusRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Booking status updated successfully");
            response.setData(updatedBooking);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
