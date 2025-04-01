package com.hacof.identity.service;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.dto.request.ChangePasswordRequest;
import com.hacof.identity.dto.request.ForgotPasswordRequest;
import com.hacof.identity.dto.request.PasswordCreateRequest;
import com.hacof.identity.dto.request.ResetPasswordRequest;
import com.hacof.identity.dto.request.UserCreateRequest;
import com.hacof.identity.dto.request.UserUpdateRequest;
import com.hacof.identity.dto.response.AvatarResponse;
import com.hacof.identity.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(String token, UserCreateRequest request);

    void createPassword(PasswordCreateRequest request);

    UserResponse getMyInfo();

    List<UserResponse> getUsers();

    UserResponse getUserById(Long id);

    UserResponse getUserByUserName(String username);

    List<UserResponse> getUsersByRoles();

    List<UserResponse> getTeamMembers();

    List<UserResponse> getUsersByCreatedByUserName(String createdByUserName);

    UserResponse updateMyInfo(UserUpdateRequest request);

    void deleteUser(Long userId);

    String addEmail(String email);

    String verifyEmail(Long userId, String otp);

    String changePassword(ChangePasswordRequest request);

    String forgotPassword(ForgotPasswordRequest request);

    String resetPassword(ResetPasswordRequest request);

    AvatarResponse uploadAvatar(MultipartFile file, Authentication authentication) throws IOException;
}
