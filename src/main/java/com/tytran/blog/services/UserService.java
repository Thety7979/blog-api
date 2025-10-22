package com.tytran.blog.services;

import java.util.List;
import java.util.UUID;

import com.tytran.blog.dto.request.ChangePasswordRequestDTO;
import com.tytran.blog.dto.request.RegisterRequestDTO;
import com.tytran.blog.dto.request.UserRequestDTO;
import com.tytran.blog.dto.response.UserDTO;
import com.tytran.blog.entity.Users;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO getUserById(UUID userid);

    Users findByEmail(String email);

    boolean existsByEmail(String email);

    UserDTO saveUser(RegisterRequestDTO request);

    UserDTO updateUser(UUID userId, UserRequestDTO request);

    UserDTO changePassword(ChangePasswordRequestDTO request);

    boolean deleteUser(UUID userId);

    UserDTO me();
}
