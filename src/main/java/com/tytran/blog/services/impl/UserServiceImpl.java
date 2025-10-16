package com.tytran.blog.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tytran.blog.repository.*;
import com.tytran.blog.services.RoleService;
import com.tytran.blog.services.UserService;
import com.tytran.blog.dto.request.RegisterRequestDTO;
import com.tytran.blog.dto.request.UserRequestDTO;
import com.tytran.blog.dto.response.UserDTO;
import com.tytran.blog.entity.Role;
import com.tytran.blog.entity.Users;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userDAO;

    @Autowired
    private RoleService roleService;

    @Override
    public Users findByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDAO.findByEmail(email).isPresent();
    }

    @Override
    public Users saveUser(RegisterRequestDTO request) {
        if (userDAO.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists with this email");
        }

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new RuntimeException("Password not null");
        }

        if (request.getFullname() == null || request.getFullname().isEmpty()) {
            throw new RuntimeException("Fullname not null");
        }

        Role role = roleService.findById(request.getRoleId());

        Users user = Users.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .fullname(request.getFullname())
                .created_at(LocalDateTime.now())
                .role(role)
                .build();
        user = userDAO.save(user);

        return user;
    }

    @Override
    public UserDTO updateUser(UserRequestDTO request) {
        Users user = userDAO.findById(request.getId()).orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            user.setEmail(request.getEmail());
        }
        user.setFullname(request.getFullname());

        if (request.getCurrentPassword() == null || request.getCurrentPassword().isEmpty()) {
            throw new IllegalArgumentException("Current password cannot be null");
        }
        if (!request.getCurrentPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("Current password not true");
        }
        if (request.getCurrentPassword() != null && !request.getCurrentPassword().isEmpty()) {
            user.setPassword(request.getNewPassword());
        }
        user.setUpdated_at(LocalDateTime.now());
        user = userDAO.save(user);

        return UserDTO.builder()
                .email(user.getEmail())
                .fullname(user.getFullname())
                .roleName(user.getRole().getName())
                .build();
    }

    @Override
    public boolean deleteUser(UUID id) {
        Users user = userDAO.findById(id).orElseThrow(() -> new RuntimeException("User not found with id:" + id));
        userDAO.delete(user);
        return true;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<Users> users = userDAO.findAll();
        List<UserDTO> userDTO = users.stream()
                .map(user -> new UserDTO(user.getEmail(), user.getFullname(), user.getRole() != null ? user.getRole().getName() : null))
                /*
                học thêm một số phương thức trong stream()

                Lọc ra phần tử thỏa điều kiện
                .filter(u -> "admin".equals(u.getRoleName())) 

                sắp xếp theo phần tử, như hiện tại là tăng dần từ A->Z, có thể thêm .reversed() để sắp xếp ngược lại
                .sorted(Comparator.comparing(UserDTO::getEmail))
                */
                .collect(Collectors.toList());
        return userDTO;
    }

}
