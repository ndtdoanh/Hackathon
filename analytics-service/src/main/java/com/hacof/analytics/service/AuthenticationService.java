package com.hacof.analytics.service;

import java.text.ParseException;

import com.hacof.analytics.dto.request.IntrospectRequest;
import com.hacof.analytics.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
