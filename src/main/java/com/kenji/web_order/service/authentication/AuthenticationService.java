package com.kenji.web_order.service.authentication;

import com.kenji.web_order.dto.request.AuthenticationRequest;
import com.kenji.web_order.dto.request.token.RefreshTokenRequest;
import com.kenji.web_order.dto.request.token.TokenRequest;
import com.kenji.web_order.dto.response.AuthenticationResponse;
import com.kenji.web_order.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    public IntrospectResponse introspect(TokenRequest request) throws JOSEException, ParseException;

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws ParseException, JOSEException;

    public void logout(TokenRequest request) throws ParseException, JOSEException;

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException;
}
