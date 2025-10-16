package com.tytran.blog.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tytran.blog.dto.request.RegisterRequestDTO;
import com.tytran.blog.dto.response.AuthResponseDTO;
import com.tytran.blog.dto.response.UserDTO;
import com.tytran.blog.entity.Users;
import com.tytran.blog.services.AuthService;
import com.tytran.blog.services.UserService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Override
    public AuthResponseDTO Register(RegisterRequestDTO request) {
        Users user = userService.saveUser(request);
        UserDTO userDTO = convertToDTO(user);
        return new AuthResponseDTO(null, userDTO);

    }

    private UserDTO convertToDTO(Users user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .fullname(user.getFullname())
                .roleName(user.getRole().getName())
                .build();
    }

}
