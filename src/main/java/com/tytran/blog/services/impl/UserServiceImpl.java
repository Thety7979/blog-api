package com.tytran.blog.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tytran.blog.repository.*;
import com.tytran.blog.services.RoleService;
import com.tytran.blog.services.UserService;
import com.tytran.blog.dto.request.ChangePasswordRequestDTO;
import com.tytran.blog.dto.request.RegisterRequestDTO;
import com.tytran.blog.dto.request.UserRequestDTO;
import com.tytran.blog.dto.response.UserDTO;
import com.tytran.blog.entity.Role;
import com.tytran.blog.entity.Users;
import com.tytran.blog.exception.AppException;
import com.tytran.blog.exception.ErrorCode;

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
            throw new AppException(ErrorCode.USER_EXISTED);
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
    public UserDTO updateUser(UUID userId, UserRequestDTO request) {
        Users user = userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            user.setEmail(request.getEmail());
        }
        if(request.getFullname() != null && !request.getFullname().isEmpty()){
            user.setFullname(request.getFullname());
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
    public boolean deleteUser(UUID userId) {
        Users user = userDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id:" + userId));
        userDAO.delete(user);
        return true;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<Users> users = userDAO.findAll();
        List<UserDTO> userDTO = users.stream()
                .map(user -> new UserDTO(user.getEmail(), user.getFullname(),
                        user.getRole() != null ? user.getRole().getName() : null))
                /*
                 * học thêm một số phương thức trong stream()
                 * 
                 * Lọc ra phần tử thỏa điều kiện
                 * .filter(u -> "admin".equals(u.getRoleName()))
                 * 
                 * sắp xếp theo phần tử, như hiện tại là tăng dần từ A->Z, có thể thêm
                 * .reversed() để sắp xếp ngược lại
                 * .sorted(Comparator.comparing(UserDTO::getEmail))
                 */
                .collect(Collectors.toList());
        return userDTO;
    }

    @Override
    public UserDTO getUserById(UUID userid) {
        Users user = userDAO.findById(userid).orElseThrow(() -> new RuntimeException("user not found"));
        return new UserDTO(user.getEmail(), user.getFullname(), user.getRole().getName());
    }

    @Override
    public UserDTO changePassword(UUID userId, ChangePasswordRequestDTO request) {
        Users user = userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (request.getCurrentPassword() != null && !request.getCurrentPassword().isEmpty()) {
            if (request.getCurrentPassword().equals(user.getPassword())) {
                user.setPassword(request.getNewPassword());
                userDAO.save(user);
            }else{
                throw new AppException(ErrorCode.PASSWORD_NOT_TRUE);
            }
        } else {
            throw new AppException(ErrorCode.PASSWORD_NOT_NULL);
        }
        return UserDTO.builder()
                .email(user.getEmail())
                .fullname(user.getFullname())
                .roleName(user.getRole().getName())
                .build();
    }

}
