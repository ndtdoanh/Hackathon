package com.hacof.identity.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.request.AuthenticationRequest;
import com.hacof.identity.dto.request.IntrospectRequest;
import com.hacof.identity.dto.request.LogoutRequest;
import com.hacof.identity.dto.request.RefreshRequest;
import com.hacof.identity.dto.response.AuthenticationResponse;
import com.hacof.identity.dto.response.IntrospectResponse;
import com.hacof.identity.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
class AuthenticationControllerTest {

    @Mock
    AuthenticationService authenticationService;

    @InjectMocks
    AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOutboundAuthenticate() {
        String code = "abc123";
        AuthenticationResponse mockResponse = new AuthenticationResponse();
        when(authenticationService.outboundAuthenticate(code)).thenReturn(mockResponse);

        ApiResponse<AuthenticationResponse> response = authenticationController.outboundAuthenticate(code);

        assertEquals(mockResponse, response.getData());
        verify(authenticationService, times(1)).outboundAuthenticate(code);
    }

    @Test
    void testAuthenticateWithToken() {
        AuthenticationRequest request = new AuthenticationRequest();
        AuthenticationResponse mockResponse = new AuthenticationResponse();
        when(authenticationService.authenticate(request)).thenReturn(mockResponse);

        ApiResponse<AuthenticationResponse> response = authenticationController.authenticate(request);

        assertEquals(mockResponse, response.getData());
        verify(authenticationService, times(1)).authenticate(request);
    }

    @Test
    void testIntrospect() throws ParseException, JOSEException {
        IntrospectRequest request = new IntrospectRequest();
        IntrospectResponse mockResponse = new IntrospectResponse();
        when(authenticationService.introspect(request)).thenReturn(mockResponse);

        ApiResponse<IntrospectResponse> response = authenticationController.introspect(request);

        assertEquals(mockResponse, response.getData());
        verify(authenticationService, times(1)).introspect(request);
    }

    @Test
    void testRefreshToken() throws ParseException, JOSEException {
        RefreshRequest request = new RefreshRequest();
        AuthenticationResponse mockResponse = new AuthenticationResponse();
        when(authenticationService.refreshToken(request)).thenReturn(mockResponse);

        ApiResponse<AuthenticationResponse> response = authenticationController.refresh(request);

        assertEquals(mockResponse, response.getData());
        verify(authenticationService, times(1)).refreshToken(request);
    }

    @Test
    void testLogout() throws ParseException, JOSEException {
        LogoutRequest request = new LogoutRequest();
        doNothing().when(authenticationService).logout(request);

        ApiResponse<Void> response = authenticationController.logout(request);

        assertEquals(null, response.getData());
        verify(authenticationService, times(1)).logout(request);
    }
}
