package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;

import java.util.List;

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
}
