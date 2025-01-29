package com.hacof.identity.services;

import java.text.ParseException;

import com.hacof.identity.dtos.request.AuthenticationRequest;
import com.hacof.identity.dtos.request.IntrospectRequest;
import com.hacof.identity.dtos.request.LogoutRequest;
import com.hacof.identity.dtos.request.RefreshRequest;
import com.hacof.identity.dtos.response.AuthenticationResponse;
import com.hacof.identity.dtos.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    AuthenticationResponse outboundAuthenticate(String code);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
