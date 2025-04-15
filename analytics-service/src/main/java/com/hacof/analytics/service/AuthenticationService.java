package com.hacof.analytics.service;

import com.hacof.analytics.dto.request.IntrospectRequest;
import com.hacof.analytics.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
