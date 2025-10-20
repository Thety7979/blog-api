package com.tytran.blog.services.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tytran.blog.dto.request.AuthRequestDTO;
import com.tytran.blog.dto.request.RegisterRequestDTO;
import com.tytran.blog.dto.response.AuthResponseDTO;
import com.tytran.blog.dto.response.UserDTO;
import com.tytran.blog.entity.Users;
import com.tytran.blog.exception.AppException;
import com.tytran.blog.exception.ErrorCode;
import com.tytran.blog.mapper.UserMapper;
import com.tytran.blog.repository.UserRepository;
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

    UserRepository userRepository;

    UserMapper userMapper;

    @Override
    public UserDTO Register(RegisterRequestDTO request) {
        return userService.saveUser(request);
    }

    @Override
    public AuthResponseDTO Login(AuthRequestDTO request) {
        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Boolean isPasswordMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isPasswordMatch) {
            throw new AppException(ErrorCode.PASSWORD_NOT_TRUE);
        }
        return AuthResponseDTO.builder()
                .token(null)
                .user(userMapper.userToUserDTO(user))
                .build();
    }

}
