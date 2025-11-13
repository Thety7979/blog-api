package com.tytran.blog.services;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.tytran.blog.dto.request.AuthRequestDTO;
import com.tytran.blog.dto.request.IntrospectRequestDTO;
import com.tytran.blog.dto.request.LogoutRequestDTO;
import com.tytran.blog.dto.request.RefreshTokenRequestDTO;
import com.tytran.blog.dto.request.RegisterRequestDTO;
import com.tytran.blog.dto.response.AuthResponseDTO;
import com.tytran.blog.dto.response.IntrospectResponseDTO;
import com.tytran.blog.dto.response.RefreshTokenResponseDTO;
import com.tytran.blog.dto.response.UserDTO;

public interface AuthService {
    AuthResponseDTO Login(AuthRequestDTO request);

    UserDTO Register(RegisterRequestDTO request);

    IntrospectResponseDTO introspect(IntrospectRequestDTO request) throws JOSEException, ParseException;

    Boolean Logout(LogoutRequestDTO request) throws JOSEException, ParseException;

    RefreshTokenResponseDTO refreshToken(RefreshTokenRequestDTO request) throws JOSEException, ParseException;
}
