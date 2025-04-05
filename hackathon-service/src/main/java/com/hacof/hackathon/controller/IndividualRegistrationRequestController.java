package com.hacof.hackathon.controller;

// import com.hacof.hackathon.service.IndividualRegistrationRequestService;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/individuals")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class IndividualRegistrationRequestController {
    //    IndividualRegistrationRequestService individualRegistrationRequestService;
    //
    //    @PostMapping
    //    public ResponseEntity<CommonResponse<IndividualRegistrationRequestDTO>> createIndividualRegistration(
    //            @RequestBody @Valid CommonRequest<IndividualRegistrationRequestDTO> request) {
    //        IndividualRegistrationRequestDTO individualRegistrationRequestDTO =
    //                individualRegistrationRequestService.create(request.getData());
    //        CommonResponse<IndividualRegistrationRequestDTO> response = new CommonResponse<>(
    ////                request.getRequestId(),
    ////                LocalDateTime.now(),
    ////                request.getChannel(),
    //                new CommonResponse.Result("0000", "Individual registration created successfully"),
    //                individualRegistrationRequestDTO);
    //        return ResponseEntity.ok(response);
    //    }
    //
    //    @PutMapping
    //    public ResponseEntity<CommonResponse<IndividualRegistrationRequestDTO>> updateIndividualRegistration(
    //            @RequestBody @Valid CommonRequest<IndividualRegistrationRequestDTO> request) {
    //        IndividualRegistrationRequestDTO individualRegistrationRequestDTO =
    // individualRegistrationRequestService.update(
    //                Long.parseLong(request.getData().getId()), request.getData());
    //        CommonResponse<IndividualRegistrationRequestDTO> response = new CommonResponse<>(
    ////                request.getRequestId(),
    ////                LocalDateTime.now(),
    ////                request.getChannel(),
    //                new CommonResponse.Result("0000", "Individual registration updated successfully"),
    //                individualRegistrationRequestDTO);
    //        return ResponseEntity.ok(response);
    //    }
    //
    //    @DeleteMapping
    //    public ResponseEntity<CommonResponse<Void>> deleteIndividualRegistration(
    //            @RequestBody @Valid CommonRequest<Long> request) {
    //        individualRegistrationRequestService.delete(request.getData());
    //        CommonResponse<Void> response = new CommonResponse<>(
    ////                request.getRequestId(),
    ////                LocalDateTime.now(),
    ////                request.getChannel(),
    //                new CommonResponse.Result("0000", "Individual registration deleted successfully"),
    //                null);
    //        return ResponseEntity.ok(response);
    //    }
    //
    //    @GetMapping
    //    public ResponseEntity<CommonResponse<List<IndividualRegistrationRequestDTO>>> getAllIndividualRegistrations()
    // {
    //        List<IndividualRegistrationRequestDTO> individualRegistrations =
    // individualRegistrationRequestService.getAll();
    //        CommonResponse<List<IndividualRegistrationRequestDTO>> response = new CommonResponse<>(
    ////                UUID.randomUUID().toString(),
    ////                LocalDateTime.now(),
    ////                "HACOF",
    //                new CommonResponse.Result("0000", "Fetched all individual registrations successfully"),
    //                individualRegistrations);
    //        return ResponseEntity.ok(response);
    //    }
    //
    //    @GetMapping("/{id}")
    //    public ResponseEntity<CommonResponse<IndividualRegistrationRequestDTO>> getIndividualRegistrationById(
    //            @PathVariable Long id) {
    //        IndividualRegistrationRequestDTO individualRegistration =
    // individualRegistrationRequestService.getById(id);
    //        CommonResponse<IndividualRegistrationRequestDTO> response = new CommonResponse<>(
    ////                UUID.randomUUID().toString(),
    ////                LocalDateTime.now(),
    ////                "HACOF",
    //                new CommonResponse.Result("0000", "Fetched individual registration successfully"),
    //                individualRegistration);
    //        return ResponseEntity.ok(response);
    //    }
    //
    //    @GetMapping("/filter-by-username")
    //    public ResponseEntity<CommonResponse<List<IndividualRegistrationRequestDTO>>> getAllByCreatedByUsername(
    //            @RequestParam String createdByUsername) {
    //        List<IndividualRegistrationRequestDTO> requests =
    //                individualRegistrationRequestService.getAllByCreatedByUsername(createdByUsername);
    //        return ResponseEntity.ok(new CommonResponse<>(
    ////                UUID.randomUUID().toString(),
    ////                LocalDateTime.now(),
    ////                "HACOF",
    //                new CommonResponse.Result("0000", "Fetched individual registration requests successfully"),
    //                requests));
    //    }
    //
    //    @GetMapping("/filter-by-username-and-hackathon")
    //    public ResponseEntity<CommonResponse<List<IndividualRegistrationRequestDTO>>>
    //            getAllByCreatedByUsernameAndHackathonId(
    //                    @RequestParam String createdByUsername, @RequestParam String hackathonId) {
    //        List<IndividualRegistrationRequestDTO> requests =
    //                individualRegistrationRequestService.getAllByCreatedByUsernameAndHackathonId(
    //                        createdByUsername, hackathonId);
    //        return ResponseEntity.ok(new CommonResponse<>(
    ////                UUID.randomUUID().toString(),
    ////                LocalDateTime.now(),
    ////                "HACOF",
    //                new CommonResponse.Result("0000", "Fetched individual registration requests successfully"),
    //                requests));
    //    }
}
