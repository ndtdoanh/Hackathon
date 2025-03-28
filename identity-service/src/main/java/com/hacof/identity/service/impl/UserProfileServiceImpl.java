package com.hacof.identity.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.dto.request.UserProfileCreateRequest;
import com.hacof.identity.dto.request.UserProfileUpdateRequest;
import com.hacof.identity.dto.response.UserProfileResponse;
import com.hacof.identity.entity.User;
import com.hacof.identity.entity.UserProfile;
import com.hacof.identity.exception.AppException;
import com.hacof.identity.exception.ErrorCode;
import com.hacof.identity.mapper.UserProfileMapper;
import com.hacof.identity.repository.UserProfileRepository;
import com.hacof.identity.repository.UserRepository;
import com.hacof.identity.service.UserProfileService;
import com.hacof.identity.util.SecurityUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileServiceImpl implements UserProfileService {
    String UPLOAD_DIR = "D:\\ki 9\\uploads\\avatars\\";
    List<String> ALLOWED_FILE_TYPES = Arrays.asList("image/jpeg", "image/png");

    UserProfileRepository userProfileRepository;
    UserRepository userRepository;
    UserProfileMapper userProfileMapper;

    Long getCurrentUserId() {
        String currentUsername = SecurityUtil.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED_PROFILE_ACCESS));

        return userRepository
                .findByUsername(currentUsername)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED))
                .getId();
    }

    @Override
    @Transactional
    public UserProfileResponse createProfile(UserProfileCreateRequest request) {
        Long userId = getCurrentUserId();

        if (userProfileRepository.existsByUserId(userId)) {
            throw new AppException(ErrorCode.PROFILE_ALREADY_EXISTS);
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        UserProfile userProfile = userProfileMapper.toEntity(request);
        userProfile.setUser(user);

        userProfileRepository.save(userProfile);
        return userProfileMapper.toResponse(userProfile);
    }

    @Override
    @Transactional
    public UserProfileResponse updateProfile(UserProfileUpdateRequest request) {
        Long userId = getCurrentUserId();

        UserProfile userProfile = userProfileRepository
                .findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));

        userProfileMapper.updateEntity(userProfile, request);
        userProfileRepository.save(userProfile);
        return userProfileMapper.toResponse(userProfile);
    }

    @Override
    public UserProfileResponse getProfileByUserId(Long userId) {
        UserProfile userProfile = userProfileRepository
                .findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));

        return userProfileMapper.toResponse(userProfile);
    }

    @Override
    public UserProfileResponse getProfile(Long userId) {
        UserProfile userProfile = userProfileRepository
                .findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));
        return userProfileMapper.toResponse(userProfile);
    }

    @Override
    public List<UserProfileResponse> getProfiles() {
        return userProfileRepository.findAll().stream()
                .map(userProfileMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteProfile() {
        Long userId = getCurrentUserId();

        UserProfile userProfile = userProfileRepository
                .findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));
        userProfileRepository.delete(userProfile);
    }

    @Override
    @Transactional
    public UserProfileResponse uploadAvatar(MultipartFile file) {
        Long userId = getCurrentUserId();

        if (!ALLOWED_FILE_TYPES.contains(file.getContentType())) {
            throw new AppException(ErrorCode.INVALID_FILE_FORMAT);
        }

        UserProfile userProfile = userProfileRepository
                .findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(UPLOAD_DIR);

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            if (userProfile.getAvatarUrl() != null
                    && !userProfile.getAvatarUrl().isEmpty()) {
                try {
                    String oldFilePath = userProfile.getAvatarUrl().substring(1);
                    Files.deleteIfExists(Paths.get(oldFilePath));
                } catch (IOException | SecurityException e) {
                    System.err.println("Could not delete old avatar: " + e.getMessage());
                }
            }

            userProfile.setAvatarUrl("/" + UPLOAD_DIR + fileName);
            userProfile.setUploadedAt(LocalDateTime.now());
            userProfileRepository.save(userProfile);

            return userProfileMapper.toResponse(userProfile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
}
