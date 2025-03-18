package com.hacof.identity.service;

import java.util.List;

import com.hacof.identity.dto.request.ChangePasswordRequest;
import com.hacof.identity.dto.request.ForgotPasswordRequest;
import com.hacof.identity.dto.request.PasswordCreateRequest;
import com.hacof.identity.dto.request.ResetPasswordRequest;
import com.hacof.identity.dto.request.UserCreateRequest;
import com.hacof.identity.dto.request.UserUpdateRequest;
import com.hacof.identity.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(String token, UserCreateRequest request);

    void createPassword(PasswordCreateRequest request);

    UserResponse getMyInfo();

    List<UserResponse> getUsers();

    UserResponse getUser(Long id);

    UserResponse updateMyInfo(UserUpdateRequest request);

    void deleteUser(Long userId);

    String addEmail(String email);

    String verifyEmail(Long userId, String otp);

    String changePassword(ChangePasswordRequest request);

    String forgotPassword(ForgotPasswordRequest request);

    String resetPassword(ResetPasswordRequest request);
}
