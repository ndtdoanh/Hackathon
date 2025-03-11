package com.hacof.communication.services;

import java.text.ParseException;

import com.hacof.communication.dto.request.IntrospectRequest;
import com.hacof.communication.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
