package com.hacof.identity.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.dtos.request.UserProfileCreateRequest;
import com.hacof.identity.dtos.request.UserProfileUpdateRequest;
import com.hacof.identity.dtos.response.UserProfileResponse;

public interface UserProfileService {
    UserProfileResponse createProfile(UserProfileCreateRequest request);

    UserProfileResponse updateProfile(UserProfileUpdateRequest request);

    UserProfileResponse getProfile(Long userId);

    List<UserProfileResponse> getProfiles();

    void deleteProfile();

    UserProfileResponse uploadAvatar(MultipartFile file);
}
