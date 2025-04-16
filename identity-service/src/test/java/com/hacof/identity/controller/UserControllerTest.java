package com.hacof.identity.controller;

import com.hacof.identity.dto.ApiRequest;
import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.request.AddEmailRequest;
import com.hacof.identity.dto.request.ChangePasswordRequest;
import com.hacof.identity.dto.request.ForgotPasswordRequest;
import com.hacof.identity.dto.request.OrganizerUpdateForJudgeMentor;
import com.hacof.identity.dto.request.PasswordCreateRequest;
import com.hacof.identity.dto.request.ResetPasswordRequest;
import com.hacof.identity.dto.request.UserCreateRequest;
import com.hacof.identity.dto.request.UserUpdateRequest;
import com.hacof.identity.dto.request.VerifyEmailRequest;
import com.hacof.identity.dto.response.AvatarResponse;
import com.hacof.identity.dto.response.UserResponse;
import com.hacof.identity.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testCreateUser(){
        ApiRequest<UserCreateRequest> request = new ApiRequest<>();
        request.setData(new UserCreateRequest("username", "password", "firstName", "lastName", null));

        UserResponse userResponse = new UserResponse();
        when(userService.createUser(any(), any())).thenReturn(userResponse);

        ResponseEntity<ApiResponse> response = userController.createUser("Bearer token", request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService).createUser(any(), any());
    }

    @Test
    void testCreatePassword() {
        ApiRequest<PasswordCreateRequest> request = new ApiRequest<>();
        request.setData(new PasswordCreateRequest("password"));

        doNothing().when(userService).createPassword(any());

        ResponseEntity<ApiResponse<Void>> response = userController.createPassword(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService).createPassword(any());
    }

    @Test
    void testGetUsers() {
        when(userService.getUsers()).thenReturn(List.of(new UserResponse()));

        ApiResponse<List<UserResponse>> response = userController.getUsers();

        assertNotNull(response.getData());
        verify(userService).getUsers();
    }

    @Test
    void testGetUserById() {
        long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(new UserResponse());

        ApiResponse<UserResponse> response = userController.getUser(userId);

        assertNotNull(response.getData());
        verify(userService).getUserById(userId);
    }

    @Test
    void testGetUserByUsername() {
        String username = "testUser";
        when(userService.getUserByUserName(username)).thenReturn(new UserResponse());

        ApiResponse<UserResponse> response = userController.getUser(username);

        assertNotNull(response.getData());
        verify(userService).getUserByUserName(username);
    }

    @Test
    void testGetUsersByRoles() {
        when(userService.getUsersByRoles()).thenReturn(List.of(new UserResponse()));

        ApiResponse<List<UserResponse>> response = userController.getUsersByRoles();

        assertNotNull(response.getData());
        verify(userService).getUsersByRoles();
    }

    @Test
    void testGetTeamMembers() {
        when(userService.getTeamMembers()).thenReturn(List.of(new UserResponse()));

        ApiResponse<List<UserResponse>> response = userController.getTeamMembers();

        assertNotNull(response.getData());
        verify(userService).getTeamMembers();
    }

    @Test
    void testGetUsersByCreatedByUserName() {
        String createdByUserName = "organizer";
        when(userService.getUsersByCreatedByUserName(createdByUserName)).thenReturn(List.of(new UserResponse()));

        ApiResponse<List<UserResponse>> response = userController.getUsersByCreatedByUserName(createdByUserName);

        assertNotNull(response.getData());
        verify(userService).getUsersByCreatedByUserName(createdByUserName);
    }

    @Test
    void testGetMyInfo() {
        when(userService.getMyInfo()).thenReturn(new UserResponse());

        ApiResponse<UserResponse> response = userController.getMyInfo();

        assertNotNull(response.getData());
        verify(userService).getMyInfo();
    }

    @Test
    void testUpdateUser() {
        ApiRequest<UserUpdateRequest> request = new ApiRequest<>();
        request.setData(new UserUpdateRequest("firstName", "lastName", "1234567890", "bio", null));

        when(userService.updateMyInfo(any())).thenReturn(new UserResponse());

        ApiResponse<UserResponse> response = userController.updateMyInfo(request);

        assertNotNull(response.getData());
        verify(userService).updateMyInfo(any());
    }

    @Test
    void testUpdateJudgeMentorByOrganizer() {
        long userId = 1L;
        ApiRequest<OrganizerUpdateForJudgeMentor> request = new ApiRequest<>();
        request.setData(new OrganizerUpdateForJudgeMentor("firstName", "lastName", "role"));

        when(userService.updateJudgeMentorByOrganization(eq(userId), any(OrganizerUpdateForJudgeMentor.class)))
                .thenReturn(new UserResponse());

        ApiResponse<UserResponse> response = userController.updateJudgeMentorByOrganizer(userId, request);

        assertNotNull(response.getData());
        verify(userService).updateJudgeMentorByOrganization(eq(userId), any(OrganizerUpdateForJudgeMentor.class));
    }

    @Test
    void testDeleteUser() {
        long userId = 1L;
        doNothing().when(userService).deleteUser(userId);

        ApiResponse<String> response = userController.deleteUser(userId);

        assertEquals("User has been deleted", response.getData());
        verify(userService).deleteUser(userId);
    }

    @Test
    void testAddEmail() {
        ApiRequest<AddEmailRequest> request = new ApiRequest<>();
        request.setData(new AddEmailRequest("test@example.com"));

        when(userService.addEmail(any())).thenReturn("Email added");

        ResponseEntity<ApiResponse<String>> response = userController.addEmail(request, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService).addEmail(any());
    }

    @Test
    void testVerifyEmail() {
        ApiRequest<VerifyEmailRequest> request = new ApiRequest<>();
        request.setData(new VerifyEmailRequest("123456"));
        request.setRequestId("test-request-id");
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("test-channel");

        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaim("user_id")).thenReturn(1L);

        String verifyResult = "Email test@example.com has been verified successfully";
        when(userService.verifyEmail(eq(1L), eq("123456"))).thenReturn(verifyResult);

        ResponseEntity<ApiResponse<String>> response = userController.verifyEmail(request, jwt);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(verifyResult, response.getBody().getMessage());

        verify(userService).verifyEmail(eq(1L), eq("123456"));
    }

    @Test
    void testChangePassword() {
        ApiRequest<ChangePasswordRequest> request = new ApiRequest<>();
        request.setData(new ChangePasswordRequest("currentPassword", "newPassword", "confirmPassword"));

        when(userService.changePassword(any())).thenReturn("Password changed successfully");

        ApiResponse<String> response = userController.changePassword(request);

        assertEquals("Password changed successfully", response.getMessage());
        verify(userService).changePassword(any());
    }

    @Test
    void testForgotPassword() {
        ApiRequest<ForgotPasswordRequest> request = new ApiRequest<>();
        request.setData(new ForgotPasswordRequest("email@example.com"));

        when(userService.forgotPassword(any())).thenReturn("Password reset link sent");

        ApiResponse<String> response = userController.forgotPassword(request);

        assertEquals("Password reset link sent", response.getMessage());
        verify(userService).forgotPassword(any());
    }

    @Test
    void testResetPassword() {
        ApiRequest<ResetPasswordRequest> request = new ApiRequest<>();
        request.setData(new ResetPasswordRequest("email", "otp", "newPassword", "confirmPassword"));

        when(userService.resetPassword(any())).thenReturn("Password reset successful");

        ApiResponse<String> response = userController.resetPassword(request);

        assertEquals("Password reset successful", response.getMessage());
        verify(userService).resetPassword(any());
    }

    @Test
    void testUploadAvatar() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        Authentication authentication = mock(Authentication.class);
        AvatarResponse avatarResponse = new AvatarResponse("1", "http://example.com/avatar.jpg", LocalDateTime.now());

        when(userService.uploadAvatar(any(), any())).thenReturn(avatarResponse);

        ResponseEntity<ApiResponse<AvatarResponse>> response = userController.uploadAvatar(file, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService).uploadAvatar(any(), any());
    }
}
