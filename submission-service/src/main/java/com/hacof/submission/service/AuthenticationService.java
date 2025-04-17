package com.hacof.submission.service;

import java.text.ParseException;

import com.hacof.submission.dto.request.IntrospectRequest;
import com.hacof.submission.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
