package com.hacof.identity.service;

import java.text.ParseException;

import com.hacof.identity.dto.request.AuthenticationRequest;
import com.hacof.identity.dto.request.IntrospectRequest;
import com.hacof.identity.dto.request.LogoutRequest;
import com.hacof.identity.dto.request.RefreshRequest;
import com.hacof.identity.dto.response.AuthenticationResponse;
import com.hacof.identity.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    AuthenticationResponse outboundAuthenticate(String code);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
