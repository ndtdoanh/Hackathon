package com.hacof.hackathon.service.impl;

import com.hacof.hackathon.service.IdentityClientService;
import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.response.UserResponse;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class IdentityClientServiceImpl implements IdentityClientService {

    IdentityClientService identityClient;

    @Override
    public ApiResponse<List<UserResponse>> getUsers(String token) {
        return identityClient.getUsers(token);
    }

    @Override
    public ApiResponse<UserResponse> getUserById(String token, String userId) {
        return identityClient.getUserById(token, userId);
    }

    @Override
    public ApiResponse<UserResponse> getUserByUsername(String token, String username) {
        return identityClient.getUserByUsername(token, username);
    }

    @Override
    public ApiResponse<List<UserResponse>> getUsersByRoles(String token) {
        return identityClient.getUsersByRoles(token);
    }

    @Override
    public ApiResponse<List<UserResponse>> getTeamMembers(String token) {
        return identityClient.getTeamMembers(token);
    }

    @Override
    public ApiResponse<List<UserResponse>> getUsersByCreatedByUserName(String token, String createdByUserName) {
        return identityClient.getUsersByCreatedByUserName(token, createdByUserName);
    }
}
