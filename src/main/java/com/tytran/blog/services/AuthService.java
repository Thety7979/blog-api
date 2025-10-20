package com.tytran.blog.services;

import com.tytran.blog.dto.request.AuthRequestDTO;
import com.tytran.blog.dto.request.RegisterRequestDTO;
import com.tytran.blog.dto.response.AuthResponseDTO;
import com.tytran.blog.dto.response.UserDTO;

public interface AuthService {
    AuthResponseDTO Login(AuthRequestDTO request);
    UserDTO Register(RegisterRequestDTO request);
}
