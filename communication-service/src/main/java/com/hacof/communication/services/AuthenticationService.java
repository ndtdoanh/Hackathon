package com.hacof.communication.services;

import com.hacof.communication.dto.request.IntrospectRequest;
import com.hacof.communication.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
