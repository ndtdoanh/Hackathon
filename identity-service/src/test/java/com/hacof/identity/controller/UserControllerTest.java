package com.hacof.identity.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.constant.Status;
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

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testCreateUser() {
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
    void testToggleUserStatus() {
        long userId = 1L;
        Status newStatus = Status.INACTIVE;

        doNothing().when(userService).toggleUserStatus(userId, newStatus);

        ApiResponse<String> response = userController.updateUserStatus(userId, newStatus);

        assertEquals("User status has been updated", response.getData());
        verify(userService).toggleUserStatus(userId, newStatus);
    }

    @Test
    void testAddEmailSuccess() {
        ApiRequest<AddEmailRequest> request = new ApiRequest<>();
        request.setData(new AddEmailRequest("test@example.com"));

        when(userService.addEmail(any())).thenReturn("Email added");

        ResponseEntity<ApiResponse<String>> response = userController.addEmail(request, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Email added", response.getBody().getMessage());
        verify(userService).addEmail(any());
    }

    @Test
    void testAddEmailInvalidEmail() {
        ApiRequest<AddEmailRequest> request = new ApiRequest<>();
        request.setData(new AddEmailRequest("invalid"));

        when(userService.addEmail(any())).thenThrow(new IllegalArgumentException("Invalid email format"));

        ResponseEntity<ApiResponse<String>> response = userController.addEmail(request, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid email format", response.getBody().getMessage());
        verify(userService).addEmail(any());
    }

    @Test
    void testAddEmailUnexpectedError() {
        ApiRequest<AddEmailRequest> request = new ApiRequest<>();
        request.setData(new AddEmailRequest("test@example.com"));

        when(userService.addEmail(any())).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<ApiResponse<String>> response = userController.addEmail(request, null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(
                "Error occurred when processing requests", response.getBody().getMessage());
        verify(userService).addEmail(any());
    }

    @Test
    void testVerifyEmailSuccess() {
        ApiRequest<VerifyEmailRequest> request = new ApiRequest<>();
        request.setData(new VerifyEmailRequest("123456"));
        request.setRequestId("test-request-id");
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("test-channel");

        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaim("user_id")).thenReturn(1L);

        String result = "Email verified";
        when(userService.verifyEmail(1L, "123456")).thenReturn(result);

        ResponseEntity<ApiResponse<String>> response = userController.verifyEmail(request, jwt);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(result, response.getBody().getMessage());
    }

    @Test
    void testVerifyEmailIllegalArgumentException() {
        ApiRequest<VerifyEmailRequest> request = new ApiRequest<>();
        request.setData(new VerifyEmailRequest("invalid"));
        request.setRequestId("id-1");
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("channel");

        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaim("user_id")).thenReturn(2L);

        when(userService.verifyEmail(2L, "invalid")).thenThrow(new IllegalArgumentException("OTP format invalid"));

        ResponseEntity<ApiResponse<String>> response = userController.verifyEmail(request, jwt);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("OTP format invalid", response.getBody().getMessage());
    }

    @Test
    void testVerifyEmailIllegalStateException() {
        ApiRequest<VerifyEmailRequest> request = new ApiRequest<>();
        request.setData(new VerifyEmailRequest("654321"));
        request.setRequestId("id-2");
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("mobile");

        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaim("user_id")).thenReturn(3L);

        when(userService.verifyEmail(3L, "654321")).thenThrow(new IllegalStateException("OTP expired"));

        ResponseEntity<ApiResponse<String>> response = userController.verifyEmail(request, jwt);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("OTP expired", response.getBody().getMessage());
    }

    @Test
    void testVerifyEmailUnexpectedException() {
        ApiRequest<VerifyEmailRequest> request = new ApiRequest<>();
        request.setData(new VerifyEmailRequest("000000"));
        request.setRequestId("id-3");
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("web");

        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaim("user_id")).thenReturn(4L);

        when(userService.verifyEmail(4L, "000000")).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<ApiResponse<String>> response = userController.verifyEmail(request, jwt);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(
                "Error occurred when processing requests", response.getBody().getMessage());
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
    void testUploadAvatar_Success() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        Authentication authentication = mock(Authentication.class);
        AvatarResponse avatarResponse = new AvatarResponse("1", "http://example.com/avatar.jpg", LocalDateTime.now());

        when(userService.uploadAvatar(any(), any())).thenReturn(avatarResponse);

        ResponseEntity<ApiResponse<AvatarResponse>> response = userController.uploadAvatar(file, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Avatar uploaded successfully", response.getBody().getMessage());
        verify(userService).uploadAvatar(file, authentication);
    }

    @Test
    void testUploadAvatar_IllegalArgumentException() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        Authentication authentication = mock(Authentication.class);

        when(userService.uploadAvatar(any(), any())).thenThrow(new IllegalArgumentException("Invalid file format"));

        ResponseEntity<ApiResponse<AvatarResponse>> response = userController.uploadAvatar(file, authentication);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid file format", response.getBody().getMessage());
        verify(userService).uploadAvatar(file, authentication);
    }

    @Test
    void testUploadAvatar_IOException() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        Authentication authentication = mock(Authentication.class);

        when(userService.uploadAvatar(any(), any())).thenThrow(new IOException("Disk full"));

        ResponseEntity<ApiResponse<AvatarResponse>> response = userController.uploadAvatar(file, authentication);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("Upload failed: Disk full"));
        verify(userService).uploadAvatar(file, authentication);
    }
}
