package com.tytran.blog.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tytran.blog.dto.ApiResponse;
import com.tytran.blog.dto.request.ChangePasswordRequestDTO;
import com.tytran.blog.dto.request.UserRequestDTO;
import com.tytran.blog.dto.response.UserDTO;
import com.tytran.blog.services.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getUsers() {
        List<UserDTO> list = userService.getAllUsers();
        ApiResponse<List<UserDTO>> response = new ApiResponse<>();
        response.setResult(list);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(
            @PathVariable UUID id, @RequestBody @Valid UserRequestDTO requestDTO) {
        ApiResponse<UserDTO> response = new ApiResponse<>();
        response.setResult(userService.updateUser(id, requestDTO));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteUser(@PathVariable UUID userId) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        response.setResult(userService.deleteUser(userId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable UUID userId) {
        ApiResponse<UserDTO> response = new ApiResponse<>();
        response.setResult(userService.getUserById(userId));
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<UserDTO>> changePassword(
            @RequestBody @Valid ChangePasswordRequestDTO requestDTO) {
        ApiResponse<UserDTO> response = new ApiResponse<>();
        response.setResult(userService.changePassword(requestDTO));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDTO>> me() {
        ApiResponse<UserDTO> response = new ApiResponse<>();
        response.setResult(userService.me());
        return ResponseEntity.ok().body(response);
    }
}
