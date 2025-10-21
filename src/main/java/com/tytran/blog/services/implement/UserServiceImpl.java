package com.tytran.blog.services.implement;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tytran.blog.repository.*;
import com.tytran.blog.services.RoleService;
import com.tytran.blog.services.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import com.tytran.blog.dto.request.ChangePasswordRequestDTO;
import com.tytran.blog.dto.request.RegisterRequestDTO;
import com.tytran.blog.dto.request.UserRequestDTO;
import com.tytran.blog.dto.response.UserDTO;
import com.tytran.blog.entity.Role;
import com.tytran.blog.entity.Users;
import com.tytran.blog.exception.AppException;
import com.tytran.blog.exception.ErrorCode;
import com.tytran.blog.mapper.UserMapper;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userDAO;

    RoleService roleService;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    @Override
    public Users findByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDAO.findByEmail(email).isPresent();
    }

    @Override
    public UserDTO saveUser(RegisterRequestDTO request) {
        if (userDAO.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        Role role = roleService.findById(request.getRoleId());

        Users user = Users.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullname(request.getFullname())
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .role(role)
                .roles(new HashSet<String>(Set.of(com.tytran.blog.enums.Role.USER.name())))
                .build();
        user = userDAO.save(user);

        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO updateUser(UUID userId, UserRequestDTO request) {
        Users user = userDAO.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            user.setEmail(request.getEmail());
        }
        if (request.getFullname() != null && !request.getFullname().isEmpty()) {
            user.setFullname(request.getFullname());
        }
        user.setUpdated_at(LocalDateTime.now());
        user = userDAO.save(user);

        return userMapper.userToUserDTO(user);
    }

    @Override
    public boolean deleteUser(UUID userId) {
        Users user = userDAO.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userDAO.delete(user);
        return true;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<Users> users = userDAO.findAll();
        return userMapper.listUserToUserDTO(users);
    }

    @Override
    public UserDTO getUserById(UUID userid) {
        Users user = userDAO.findById(userid).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO changePassword(UUID userId, ChangePasswordRequestDTO request) {
        Users user = userDAO.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (request.getCurrentPassword() != null && !request.getCurrentPassword().isEmpty()) {
            if (request.getCurrentPassword().equals(user.getPassword())) {
                user.setPassword(request.getNewPassword());
                user.setUpdated_at(LocalDateTime.now());
                userDAO.save(user);
            } else {
                throw new AppException(ErrorCode.PASSWORD_NOT_TRUE);
            }
        } else {
            throw new AppException(ErrorCode.PASSWORD_NOT_NULL);
        }
        return userMapper.userToUserDTO(user);
    }

}
