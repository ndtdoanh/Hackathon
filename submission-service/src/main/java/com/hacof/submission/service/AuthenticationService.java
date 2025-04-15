package com.hacof.submission.service;

import com.hacof.submission.dto.request.IntrospectRequest;
import com.hacof.submission.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
