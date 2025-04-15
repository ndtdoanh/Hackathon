package com.hacof.identity.service.impl;

import com.hacof.identity.dto.request.UserHackathonBulkRequestDTO;
import com.hacof.identity.dto.request.UserHackathonRequestDTO;
import com.hacof.identity.dto.response.HackathonSimpleResponse;
import com.hacof.identity.dto.response.UserHackathonResponseDTO;
import com.hacof.identity.dto.response.UserSimpleResponse;
import com.hacof.identity.entity.Hackathon;
import com.hacof.identity.entity.User;
import com.hacof.identity.entity.UserHackathon;
import com.hacof.identity.exception.AppException;
import com.hacof.identity.exception.ErrorCode;
import com.hacof.identity.repository.HackathonRepository;
import com.hacof.identity.repository.UserHackathonRepository;
import com.hacof.identity.repository.UserRepository;
import com.hacof.identity.service.UserHackathonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserHackathonServiceImpl implements UserHackathonService {
    UserRepository userRepository;
    HackathonRepository hackathonRepository;
    UserHackathonRepository userHackathonRepository;

    @Override
    public UserHackathonResponseDTO createUserHackathon(UserHackathonRequestDTO request) {
        User user = userRepository
                .findById(Long.valueOf(request.getUserId()))
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Hackathon hackathon = hackathonRepository
                .findById(Long.valueOf(request.getHackathonId()))
                .orElseThrow(() -> new AppException(ErrorCode.HACKATHON_NOT_FOUND));

        Set<String> userRoles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().getName())
                .collect(Collectors.toSet());

        if (!userRoles.contains(request.getRole())) {
            throw new AppException(ErrorCode.INVALID_ROLE);
        }

        boolean userHackathonExists = userHackathonRepository.existsByUserIdAndHackathonId(
                Long.valueOf(request.getUserId()), Long.valueOf(request.getHackathonId()));

        if (userHackathonExists) {
            throw new AppException(ErrorCode.USER_HACKATHON_ALREADY_EXISTS);
        }

        UserHackathon userHackathon = new UserHackathon();
        userHackathon.setUser(user);
        userHackathon.setHackathon(hackathon);
        userHackathon.setRole(request.getRole());

        UserHackathon savedUserHackathon = userHackathonRepository.save(userHackathon);

        UserHackathonResponseDTO responseDTO = new UserHackathonResponseDTO();
        responseDTO.setId(String.valueOf(savedUserHackathon.getId()));

        User userResponse = savedUserHackathon.getUser();
        if (userResponse != null) {
            UserSimpleResponse userSimpleResponse = new UserSimpleResponse();
            userSimpleResponse.setId(String.valueOf(userResponse.getId()));
            userSimpleResponse.setUsername(userResponse.getUsername());
            userSimpleResponse.setEmail(userResponse.getEmail());
            userSimpleResponse.setFirstName(userResponse.getFirstName());
            userSimpleResponse.setLastName(userResponse.getLastName());
            userSimpleResponse.setAvatarUrl(userResponse.getAvatarUrl());
            responseDTO.setUser(userSimpleResponse);
        }

        Hackathon hackathonResponse = savedUserHackathon.getHackathon();
        if (hackathonResponse != null) {
            HackathonSimpleResponse hackathonSimpleResponse = new HackathonSimpleResponse();
            hackathonSimpleResponse.setId(String.valueOf(hackathonResponse.getId()));
            hackathonSimpleResponse.setTitle(hackathonResponse.getTitle());
            hackathonSimpleResponse.setBannerImageUrl(hackathonResponse.getBannerImageUrl());
            hackathonSimpleResponse.setStartDate(hackathonResponse.getStartDate());
            hackathonSimpleResponse.setEndDate(hackathonResponse.getEndDate());
            responseDTO.setHackathon(hackathonSimpleResponse);
        }

        responseDTO.setRole(savedUserHackathon.getRole());
        responseDTO.setCreatedAt(savedUserHackathon.getCreatedDate());
        responseDTO.setUpdatedAt(savedUserHackathon.getLastModifiedDate());

        return responseDTO;
    }

    @Override
    public List<UserHackathonResponseDTO> getUserHackathons() {
        List<UserHackathon> userHackathons = userHackathonRepository.findAll();

        return userHackathons.stream()
                .map(userHackathon -> {
                    UserHackathonResponseDTO responseDTO = new UserHackathonResponseDTO();
                    responseDTO.setId(String.valueOf(userHackathon.getId()));

                    User user = userHackathon.getUser();
                    if (user != null) {
                        UserSimpleResponse userSimpleResponse = new UserSimpleResponse();
                        userSimpleResponse.setId(String.valueOf(user.getId()));
                        userSimpleResponse.setUsername(user.getUsername());
                        userSimpleResponse.setEmail(user.getEmail());
                        userSimpleResponse.setFirstName(user.getFirstName());
                        userSimpleResponse.setLastName(user.getLastName());
                        userSimpleResponse.setAvatarUrl(user.getAvatarUrl());
                        responseDTO.setUser(userSimpleResponse);
                    }

                    Hackathon hackathon = userHackathon.getHackathon();
                    if (hackathon != null) {
                        HackathonSimpleResponse hackathonSimpleResponse = new HackathonSimpleResponse();
                        hackathonSimpleResponse.setId(String.valueOf(hackathon.getId()));
                        hackathonSimpleResponse.setTitle(hackathon.getTitle());
                        hackathonSimpleResponse.setBannerImageUrl(hackathon.getBannerImageUrl());
                        hackathonSimpleResponse.setStartDate(hackathon.getStartDate());
                        hackathonSimpleResponse.setEndDate(hackathon.getEndDate());
                        responseDTO.setHackathon(hackathonSimpleResponse);
                    }

                    responseDTO.setRole(userHackathon.getRole());
                    responseDTO.setCreatedAt(userHackathon.getCreatedDate());
                    responseDTO.setUpdatedAt(userHackathon.getLastModifiedDate());

                    return responseDTO;
                })
                .toList();
    }

    @Override
    public UserHackathonResponseDTO getUserHackathon(Long id) {
        UserHackathon userHackathon = userHackathonRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_HACKATHON_NOT_EXISTED));

        UserHackathonResponseDTO responseDTO = new UserHackathonResponseDTO();
        responseDTO.setId(String.valueOf(userHackathon.getId()));

        User user = userHackathon.getUser();
        if (user != null) {
            UserSimpleResponse userSimpleResponse = new UserSimpleResponse();
            userSimpleResponse.setId(String.valueOf(user.getId()));
            userSimpleResponse.setUsername(user.getUsername());
            userSimpleResponse.setEmail(user.getEmail());
            userSimpleResponse.setFirstName(user.getFirstName());
            userSimpleResponse.setLastName(user.getLastName());
            userSimpleResponse.setAvatarUrl(user.getAvatarUrl());
            responseDTO.setUser(userSimpleResponse);
        }

        Hackathon hackathon = userHackathon.getHackathon();
        if (hackathon != null) {
            HackathonSimpleResponse hackathonSimpleResponse = new HackathonSimpleResponse();
            hackathonSimpleResponse.setId(String.valueOf(hackathon.getId()));
            hackathonSimpleResponse.setTitle(hackathon.getTitle());
            hackathonSimpleResponse.setBannerImageUrl(hackathon.getBannerImageUrl());
            hackathonSimpleResponse.setStartDate(hackathon.getStartDate());
            hackathonSimpleResponse.setEndDate(hackathon.getEndDate());
            responseDTO.setHackathon(hackathonSimpleResponse);
        }

        responseDTO.setRole(userHackathon.getRole());
        responseDTO.setCreatedAt(userHackathon.getCreatedDate());
        responseDTO.setUpdatedAt(userHackathon.getLastModifiedDate());

        return responseDTO;
    }

    @Override
    public List<UserHackathonResponseDTO> getUserHackathonsByHackathonId(Long hackathonId) {
        List<UserHackathon> userHackathons = userHackathonRepository.findByHackathon_Id(hackathonId);

        return userHackathons.stream()
                .map(userHackathon -> {
                    UserHackathonResponseDTO responseDTO = new UserHackathonResponseDTO();
                    responseDTO.setId(String.valueOf(userHackathon.getId()));

                    User user = userHackathon.getUser();
                    if (user != null) {
                        UserSimpleResponse userSimpleResponse = new UserSimpleResponse();
                        userSimpleResponse.setId(String.valueOf(user.getId()));
                        userSimpleResponse.setUsername(user.getUsername());
                        userSimpleResponse.setEmail(user.getEmail());
                        userSimpleResponse.setFirstName(user.getFirstName());
                        userSimpleResponse.setLastName(user.getLastName());
                        userSimpleResponse.setAvatarUrl(user.getAvatarUrl());
                        responseDTO.setUser(userSimpleResponse);
                    }

                    Hackathon hackathon = userHackathon.getHackathon();
                    if (hackathon != null) {
                        HackathonSimpleResponse hackathonSimpleResponse = new HackathonSimpleResponse();
                        hackathonSimpleResponse.setId(String.valueOf(hackathon.getId()));
                        hackathonSimpleResponse.setTitle(hackathon.getTitle());
                        hackathonSimpleResponse.setBannerImageUrl(hackathon.getBannerImageUrl());
                        hackathonSimpleResponse.setStartDate(hackathon.getStartDate());
                        hackathonSimpleResponse.setEndDate(hackathon.getEndDate());
                        responseDTO.setHackathon(hackathonSimpleResponse);
                    }

                    responseDTO.setRole(userHackathon.getRole());
                    responseDTO.setCreatedAt(userHackathon.getCreatedDate());
                    responseDTO.setUpdatedAt(userHackathon.getLastModifiedDate());

                    return responseDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserHackathonResponseDTO> getUserHackathonsByHackathonIdAndRoles(Long hackathonId, List<String> roles) {
        List<UserHackathon> userHackathons = userHackathonRepository.findByHackathonIdAndRoleIn(hackathonId, roles);

        return userHackathons.stream()
                .map(userHackathon -> {
                    UserHackathonResponseDTO responseDTO = new UserHackathonResponseDTO();
                    responseDTO.setId(String.valueOf(userHackathon.getId()));

                    User user = userHackathon.getUser();
                    if (user != null) {
                        UserSimpleResponse userSimpleResponse = new UserSimpleResponse();
                        userSimpleResponse.setId(String.valueOf(user.getId()));
                        userSimpleResponse.setUsername(user.getUsername());
                        userSimpleResponse.setEmail(user.getEmail());
                        userSimpleResponse.setFirstName(user.getFirstName());
                        userSimpleResponse.setLastName(user.getLastName());
                        userSimpleResponse.setAvatarUrl(user.getAvatarUrl());
                        responseDTO.setUser(userSimpleResponse);
                    }

                    Hackathon hackathon = userHackathon.getHackathon();
                    if (hackathon != null) {
                        HackathonSimpleResponse hackathonSimpleResponse = new HackathonSimpleResponse();
                        hackathonSimpleResponse.setId(String.valueOf(hackathon.getId()));
                        hackathonSimpleResponse.setTitle(hackathon.getTitle());
                        hackathonSimpleResponse.setBannerImageUrl(hackathon.getBannerImageUrl());
                        hackathonSimpleResponse.setStartDate(hackathon.getStartDate());
                        hackathonSimpleResponse.setEndDate(hackathon.getEndDate());
                        responseDTO.setHackathon(hackathonSimpleResponse);
                    }

                    responseDTO.setRole(userHackathon.getRole());
                    responseDTO.setCreatedAt(userHackathon.getCreatedDate());
                    responseDTO.setUpdatedAt(userHackathon.getLastModifiedDate());

                    return responseDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserHackathon(Long id) {
        UserHackathon userHackathon = userHackathonRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_HACKATHON_NOT_EXISTED));

        userHackathonRepository.delete(userHackathon);
    }

    @Override
    public List<UserHackathonResponseDTO> createBulkUserHackathon(UserHackathonBulkRequestDTO bulkRequest) {
        List<UserHackathonResponseDTO> responses = new ArrayList<>();

        for (UserHackathonRequestDTO request : bulkRequest.getRequests()) {
            User user = userRepository
                    .findById(Long.valueOf(request.getUserId()))
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            Hackathon hackathon = hackathonRepository
                    .findById(Long.valueOf(request.getHackathonId()))
                    .orElseThrow(() -> new AppException(ErrorCode.HACKATHON_NOT_FOUND));

            Set<String> userRoles = user.getUserRoles().stream()
                    .map(userRole -> userRole.getRole().getName())
                    .collect(Collectors.toSet());

            if (!userRoles.contains(request.getRole())) {
                throw new AppException(ErrorCode.INVALID_ROLE);
            }

            boolean exists = userHackathonRepository.existsByUserIdAndHackathonId(
                    Long.valueOf(request.getUserId()), Long.valueOf(request.getHackathonId()));
            if (exists) {
                throw new AppException(ErrorCode.USER_HACKATHON_ALREADY_EXISTS);
            }

            UserHackathon userHackathon = new UserHackathon();
            userHackathon.setUser(user);
            userHackathon.setHackathon(hackathon);
            userHackathon.setRole(request.getRole());

            UserHackathon saved = userHackathonRepository.save(userHackathon);

            UserHackathonResponseDTO responseDTO = new UserHackathonResponseDTO();
            responseDTO.setId(String.valueOf(saved.getId()));

            UserSimpleResponse userSimple = new UserSimpleResponse();
            userSimple.setId(String.valueOf(user.getId()));
            userSimple.setUsername(user.getUsername());
            userSimple.setEmail(user.getEmail());
            userSimple.setFirstName(user.getFirstName());
            userSimple.setLastName(user.getLastName());
            userSimple.setAvatarUrl(user.getAvatarUrl());
            responseDTO.setUser(userSimple);

            HackathonSimpleResponse hackathonSimple = new HackathonSimpleResponse();
            hackathonSimple.setId(String.valueOf(hackathon.getId()));
            hackathonSimple.setTitle(hackathon.getTitle());
            hackathonSimple.setBannerImageUrl(hackathon.getBannerImageUrl());
            hackathonSimple.setStartDate(hackathon.getStartDate());
            hackathonSimple.setEndDate(hackathon.getEndDate());
            responseDTO.setHackathon(hackathonSimple);

            responseDTO.setRole(saved.getRole());
            responseDTO.setCreatedAt(saved.getCreatedDate());
            responseDTO.setUpdatedAt(saved.getLastModifiedDate());

            responses.add(responseDTO);
        }

        return responses;
    }
}
