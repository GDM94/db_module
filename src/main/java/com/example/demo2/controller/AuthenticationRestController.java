package com.example.demo2.controller;



import com.example.communication.model.JwtAuthenticationRequest;
import com.example.demo2.security.JwtTokenUtil;
import com.example.demo2.security.JwtAuthenticationResponse;
import com.example.demo2.services.impl.JwtAuthenticationRequestImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class AuthenticationRestController {

    @Autowired
    private JwtAuthenticationRequestImpl jwtAuthenticationRequest;

    @RequestMapping(value = "public/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) throws AuthenticationException, JsonProcessingException {
        return jwtAuthenticationRequest.createAuthenticationToken(authenticationRequest, response);
    }

    @RequestMapping(value = "protected/refresh-token", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request, HttpServletResponse response) {
        return jwtAuthenticationRequest.refreshAndGetAuthenticationToken(request,response);
    }

}
