package com.example.demo2.services.impl;

import com.example.communication.model.JwtAuthenticationRequest;
import com.example.communication.model.JwtAuthenticationResponse;
import com.example.communication.services.JwtAutenticationService;
import com.example.demo2.security.JwtTokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class JwtAuthenticationRequestImpl implements JwtAutenticationService {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public ResponseEntity<JwtAuthenticationResponse> createAuthenticationToken(JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) throws AuthenticationException, JsonProcessingException {

        // Effettuo l autenticazione
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Genero Token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        // Ritorno il token
        //response.setHeader(tokenHeader,token);
        HttpHeaders headers = new HttpHeaders();
        headers.set(tokenHeader, token);
        final JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse(userDetails.getUsername(),userDetails.getAuthorities());
        return new ResponseEntity<JwtAuthenticationResponse>(jwtResponse, headers, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<JwtAuthenticationResponse> refreshAndGetAuthenticationToken(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(tokenHeader);
        UserDetails userDetails =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            response.setHeader(tokenHeader,refreshedToken);
            HttpHeaders headers = new HttpHeaders();
            headers.set(tokenHeader, token);
            final JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse(userDetails.getUsername(),userDetails.getAuthorities());
            return new ResponseEntity<JwtAuthenticationResponse>(jwtResponse, headers, HttpStatus.CREATED);
        } else {
            return null;
        }
    }

}
