package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.IndividualRegistrationBulkRequestDTO;
import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;

public interface IndividualRegistrationRequestService {
    IndividualRegistrationRequestDTO create(IndividualRegistrationRequestDTO individualRegistrationRequestDTO);

    IndividualRegistrationRequestDTO update(Long id, IndividualRegistrationRequestDTO individualRegistrationRequestDTO);

    void delete(Long id);

    List<IndividualRegistrationRequestDTO> getAll();

    IndividualRegistrationRequestDTO getById(Long id);

    // Methods for filtering
    List<IndividualRegistrationRequestDTO> getAllByCreatedByUsername(String createdByUsername);

    List<IndividualRegistrationRequestDTO> getAllByCreatedByUsernameAndHackathonId(
            String createdByUsername, String hackathonId);

    List<IndividualRegistrationRequestDTO> getAllByHackathonId(String hackathonId);

    List<IndividualRegistrationRequestDTO> getAllByHackathonIdAndStatusApproved(String hackathonId);

    List<IndividualRegistrationRequestDTO> bulkUpdate(List<IndividualRegistrationRequestDTO> requestDTOs);

    List<IndividualRegistrationRequestDTO> getAllByHackathonIdAndStatusCompleted(String hackathonId);

    List<IndividualRegistrationRequestDTO> getAllByHackathonIdAndStatusPending(String hackathonId);

    List<IndividualRegistrationRequestDTO> createBulk(List<IndividualRegistrationBulkRequestDTO> bulkRequestDTOList);
}
