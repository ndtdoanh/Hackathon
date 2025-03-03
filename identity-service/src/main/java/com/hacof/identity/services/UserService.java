package com.hacof.identity.services;

import java.util.List;

import com.hacof.identity.dtos.request.PasswordCreateRequest;
import com.hacof.identity.dtos.request.UserCreateRequest;
import com.hacof.identity.dtos.request.UserUpdateRequest;
import com.hacof.identity.dtos.response.UserResponse;
import com.hacof.identity.entities.User;

public interface UserService {
    UserResponse createUser(String token, UserCreateRequest request);

    void createPassword(PasswordCreateRequest request);

    UserResponse getMyInfo();

    List<UserResponse> getUsers();

    UserResponse getUser(Long id);

    UserResponse updateUser(Long userId, UserUpdateRequest request);

    void deleteUser(Long userId);

    String addEmail(User user, String email);

    String verifyEmail(User user, String otp);
}
