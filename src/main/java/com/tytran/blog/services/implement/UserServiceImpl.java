package com.tytran.blog.services.implement;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tytran.blog.repository.*;
import com.tytran.blog.services.UserCleanupService;
import com.tytran.blog.services.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    RoleRepository roleRepository;

    UserCleanupService userCleanupService;

    @Override
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public UserDTO saveUser(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        Set<Role> role = roleRepository.findByName(com.tytran.blog.enums.Role.USER.name());

        Users user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreated_at(LocalDateTime.now());
        user.setUpdated_at(LocalDateTime.now());
        user.setRoles(role);
        user = userRepository.save(user);

        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO updateUser(UUID id, UserRequestDTO request) {
        Users userId = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        authorizeUserAction(id);
        userMapper.updateToUser(request, userId);
        userId.setUpdated_at(LocalDateTime.now());
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            List<Role> roles = roleRepository.findAllByNameIn(request.getRoles());
            userId.setRoles(new HashSet<>(roles));
        }
        userId = userRepository.save(userId);
        return userMapper.userToUserDTO(userId);
    }

    @Override
    @Transactional
    public boolean deleteUser(UUID userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        authorizeUserAction(userId);
        userCleanupService.cleanupUserData(userId);
        userRepository.delete(user);
        return true;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<Users> users = userRepository.findAll();
        return users.stream().map(user -> userMapper.userToUserDTO(user)).toList();
    }

    @Override
    public UserDTO getUserById(UUID userid) {
        var securityContext = SecurityContextHolder.getContext().getAuthentication();
        Users userContext = userRepository.findByEmail(securityContext.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Users userId = userRepository.findById(userid).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (userId.getId() != userContext.getId()) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        return userMapper.userToUserDTO(userId);
    }

    @Override
    public UserDTO changePassword(ChangePasswordRequestDTO request) {
        var securityContext = SecurityContextHolder.getContext();
        String name = securityContext.getAuthentication().getName();
        Users user = userRepository.findByEmail(name).orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));
        if (request.getCurrentPassword() != null && !request.getCurrentPassword().isEmpty()) {
            boolean isPasswordMatch = passwordEncoder.matches(request.getCurrentPassword(), user.getPassword());
            if (isPasswordMatch) {
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                user.setUpdated_at(LocalDateTime.now());
                userRepository.save(user);
            } else {
                throw new AppException(ErrorCode.PASSWORD_NOT_TRUE);
            }
        } else {
            throw new AppException(ErrorCode.PASSWORD_NOT_NULL);
        }
        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO me() {
        var securityContextHolder = SecurityContextHolder.getContext();
        String name = securityContextHolder.getAuthentication().getName();
        Users user = userRepository.findByEmail(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.userToUserDTO(user);
    }

    private void authorizeUserAction(UUID userIdTarget) {
        var userContext = SecurityContextHolder.getContext().getAuthentication();
        Users users = userRepository.findByEmail(userContext.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (!Objects.equals(userIdTarget, users.getId()) && users.getRoles().stream()
                .noneMatch(r -> r.getName().equalsIgnoreCase(com.tytran.blog.enums.Role.ADMIN.name()))) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }
}
