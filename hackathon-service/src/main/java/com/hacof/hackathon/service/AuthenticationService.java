package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.IntrospectRequest;
import com.hacof.hackathon.dto.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
