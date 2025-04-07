package com.hacof.identity.service;

import java.util.List;

import com.hacof.identity.dto.request.UserHackathonBulkRequestDTO;
import com.hacof.identity.dto.request.UserHackathonRequestDTO;
import com.hacof.identity.dto.response.UserHackathonResponseDTO;

public interface UserHackathonService {
    UserHackathonResponseDTO createUserHackathon(UserHackathonRequestDTO request);

    List<UserHackathonResponseDTO> getUserHackathons();

    UserHackathonResponseDTO getUserHackathon(Long id);

    List<UserHackathonResponseDTO> getUserHackathonsByHackathonId(Long hackathonId);

    List<UserHackathonResponseDTO> getUserHackathonsByHackathonIdAndRoles(Long hackathonId, List<String> roles);

    void deleteUserHackathon(Long id);

    List<UserHackathonResponseDTO> createBulkUserHackathon(UserHackathonBulkRequestDTO bulkRequest);
}
