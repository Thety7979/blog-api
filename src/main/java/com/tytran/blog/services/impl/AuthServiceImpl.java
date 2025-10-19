package com.tytran.blog.services.impl;

import org.springframework.stereotype.Service;

import com.tytran.blog.dto.request.RegisterRequestDTO;
import com.tytran.blog.dto.response.AuthResponseDTO;
import com.tytran.blog.dto.response.UserDTO;
import com.tytran.blog.entity.Users;
import com.tytran.blog.services.AuthService;
import com.tytran.blog.services.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {

    UserService userService;

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
