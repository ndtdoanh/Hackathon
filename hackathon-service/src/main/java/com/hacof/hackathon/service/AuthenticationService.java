package com.hacof.hackathon.service;

import java.text.ParseException;

import com.hacof.hackathon.dto.IntrospectRequest;
import com.hacof.hackathon.dto.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
