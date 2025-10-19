package com.tytran.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tytran.blog.dto.ApiResponse;
import com.tytran.blog.dto.request.ChangePasswordRequestDTO;
import com.tytran.blog.dto.request.UserRequestDTO;
import com.tytran.blog.dto.response.UserDTO;
import com.tytran.blog.services.UserService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getUsers() {
        List<UserDTO> list = userService.getAllUsers();
        ApiResponse<List<UserDTO>> response = new ApiResponse<>();
        response.setResult(list);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable UUID userId, @RequestBody @Valid UserRequestDTO requestDTO) {
        ApiResponse<UserDTO> response = new ApiResponse<>();
        response.setResult(userService.updateUser(userId, requestDTO));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteUser(@PathVariable UUID userId) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        response.setResult(userService.deleteUser(userId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID userId) {
        UserDTO response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/change-password/{userId}")
    public ResponseEntity<UserDTO> changePassword(@PathVariable UUID userId,
            @RequestBody @Valid ChangePasswordRequestDTO requestDTO) {
        UserDTO response = userService.changePassword(userId, requestDTO);
        return ResponseEntity.ok(response);
    }

}
