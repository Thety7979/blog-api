package com.tytran.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tytran.blog.dto.ApiResponse;
import com.tytran.blog.dto.request.AuthRequestDTO;
import com.tytran.blog.dto.request.RegisterRequestDTO;
import com.tytran.blog.dto.response.AuthResponseDTO;
import com.tytran.blog.dto.response.UserDTO;
import com.tytran.blog.services.AuthService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@RequestBody @Valid RegisterRequestDTO request) {
        ApiResponse<UserDTO> response = new ApiResponse<>();
        response.setResult(authService.Register(request));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@RequestBody AuthRequestDTO requestDTO) {
        ApiResponse<AuthResponseDTO> response = new ApiResponse<>();
        response.setResult(authService.Login(requestDTO));
        return ResponseEntity.ok().body(response);
    }

}
