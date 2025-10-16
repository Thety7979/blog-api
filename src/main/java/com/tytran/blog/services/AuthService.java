package com.tytran.blog.services;

import com.tytran.blog.dto.request.RegisterRequestDTO;
import com.tytran.blog.dto.response.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO Register(RegisterRequestDTO request);
}
