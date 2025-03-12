package com.hacof.identity.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.dto.request.UserProfileCreateRequest;
import com.hacof.identity.dto.request.UserProfileUpdateRequest;
import com.hacof.identity.dto.response.UserProfileResponse;

public interface UserProfileService {
    UserProfileResponse createProfile(UserProfileCreateRequest request);

    UserProfileResponse updateProfile(UserProfileUpdateRequest request);

    UserProfileResponse getProfile(Long userId);

    List<UserProfileResponse> getProfiles();

    void deleteProfile();

    UserProfileResponse uploadAvatar(MultipartFile file);
}
