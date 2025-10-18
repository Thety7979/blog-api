package com.tytran.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tytran.blog.dto.request.ChangePasswordRequestDTO;
import com.tytran.blog.dto.request.UserRequestDTO;
import com.tytran.blog.dto.response.UserDTO;
import com.tytran.blog.services.UserService;

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
    public List<UserDTO> getUsers() {
        List<UserDTO> response = userService.getAllUsers();
        return response;
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID userId, @RequestBody UserRequestDTO requestDTO) {
        UserDTO response = userService.updateUser(userId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable UUID userId) {
        Boolean response = userService.deleteUser(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID userId) {
        UserDTO response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/change-password/{userId}")
    public ResponseEntity<UserDTO> changePassword(@PathVariable UUID userId,
            @RequestBody ChangePasswordRequestDTO requestDTO) {
        UserDTO response = userService.changePassword(userId, requestDTO);
        return ResponseEntity.ok(response);
    }

}
