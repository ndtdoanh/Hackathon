package com.hacof.identity.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hacof.identity.dto.request.UserHackathonRequestDTO;
import com.hacof.identity.dto.response.UserHackathonResponseDTO;
import com.hacof.identity.entity.User;
import com.hacof.identity.entity.UserHackathon;
import com.hacof.identity.exception.AppException;
import com.hacof.identity.exception.ErrorCode;
import com.hacof.identity.mapper.UserHackathonMapper;
import com.hacof.identity.repository.HackathonRepository;
import com.hacof.identity.repository.UserHackathonRepository;
import com.hacof.identity.repository.UserRepository;
import com.hacof.identity.service.UserHackathonService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserHackathonServiceImpl implements UserHackathonService {
    UserRepository userRepository;
    HackathonRepository hackathonRepository;
    UserHackathonMapper userHackathonMapper;
    UserHackathonRepository userHackathonRepository;

    @Override
    public UserHackathonResponseDTO createUserHackathon(UserHackathonRequestDTO request) {
        User user = userRepository
                .findById(Long.valueOf(request.getUserId()))
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        hackathonRepository
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

        UserHackathon userHackathon = userHackathonMapper.toUserHackathon(request);

        UserHackathon savedUserHackathon = userHackathonRepository.save(userHackathon);

        return userHackathonMapper.toUserHackathonResponse(savedUserHackathon);
    }

    @Override
    public List<UserHackathonResponseDTO> getUserHackathons() {
        return userHackathonRepository.findAll().stream()
                .map(userHackathonMapper::toUserHackathonResponse)
                .toList();
    }

    @Override
    public UserHackathonResponseDTO getUserHackathon(Long id) {
        return userHackathonMapper.toUserHackathonResponse(userHackathonRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_HACKATHON_NOT_EXISTED)));
    }

    @Override
    public List<UserHackathonResponseDTO> getUserHackathonsByHackathonId(Long hackathonId) {
        return userHackathonRepository.findByHackathon_Id(hackathonId).stream()
                .map(userHackathonMapper::toUserHackathonResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserHackathonResponseDTO> getUserHackathonsByHackathonIdAndRoles(Long hackathonId, List<String> roles) {
        List<UserHackathon> userHackathons = userHackathonRepository.findByHackathonIdAndRoleIn(hackathonId, roles);

        return userHackathons.stream()
                .map(userHackathonMapper::toUserHackathonResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserHackathonResponseDTO updateUserHackathon(Long id, UserHackathonRequestDTO request) {
        UserHackathon existingUserHackathon = userHackathonRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_HACKATHON_NOT_EXISTED));

        User user = userRepository
                .findById(Long.valueOf(request.getUserId()))
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Set<String> userRoles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().getName())
                .collect(Collectors.toSet());

        if (!userRoles.contains(request.getRole())) {
            throw new AppException(ErrorCode.INVALID_ROLE);
        }

        existingUserHackathon.setRole(request.getRole());
        existingUserHackathon.setHackathon(hackathonRepository
                .findById(Long.valueOf(request.getHackathonId()))
                .orElseThrow(() -> new AppException(ErrorCode.HACKATHON_NOT_FOUND)));

        UserHackathon updatedUserHackathon = userHackathonRepository.save(existingUserHackathon);

        return userHackathonMapper.toUserHackathonResponse(updatedUserHackathon);
    }

    @Override
    public void deleteUserHackathon(Long id) {
        UserHackathon userHackathon = userHackathonRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_HACKATHON_NOT_EXISTED));

        userHackathonRepository.delete(userHackathon);
    }
}
