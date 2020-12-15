package com.example.demo2.services;

import com.example.communication.model.JwtAuthenticationRequest;
import com.example.communication.model.JwtAuthenticationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface JwtAutenticationService {

    public ResponseEntity<JwtAuthenticationResponse> createAuthenticationToken(JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) throws AuthenticationException, JsonProcessingException;

    public ResponseEntity<JwtAuthenticationResponse> refreshAndGetAuthenticationToken(HttpServletRequest request, HttpServletResponse response);
}
