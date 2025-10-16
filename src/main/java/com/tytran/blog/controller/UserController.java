package com.tytran.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tytran.blog.dto.request.UserRequestDTO;
import com.tytran.blog.dto.response.UserDTO;
import com.tytran.blog.services.UserService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<UserDTO> getUsers(){
        List<UserDTO> response = userService.getAllUsers();
        return response;
    }
    
    @PostMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserRequestDTO requestDTO){
        UserDTO response = userService.updateUser(requestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete")
    public ResponseEntity<Boolean> deleteUser(@RequestBody UUID id){
        Boolean response = userService.deleteUser(id);
        return ResponseEntity.ok(response);
    }
    
}
