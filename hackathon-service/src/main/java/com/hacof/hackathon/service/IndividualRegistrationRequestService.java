package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;

public interface IndividualRegistrationRequestService {
    IndividualRegistrationRequestDTO create(IndividualRegistrationRequestDTO individualRegistrationRequestDTO);

    IndividualRegistrationRequestDTO update(Long id, IndividualRegistrationRequestDTO individualRegistrationRequestDTO);

    void delete(Long id);

    List<IndividualRegistrationRequestDTO> getAll();

    IndividualRegistrationRequestDTO getById(Long id);

    List<IndividualRegistrationRequestDTO> getAllByCreatedByUsername(String createdByUsername);

    List<IndividualRegistrationRequestDTO> getAllByCreatedByUsernameAndHackathonId(String createdByUsername, String hackathonId);
}

