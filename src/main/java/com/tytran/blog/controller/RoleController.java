package com.tytran.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tytran.blog.dto.ApiResponse;
import com.tytran.blog.dto.request.RoleRequestDTO;
import com.tytran.blog.dto.response.RoleResponseDTO;
import com.tytran.blog.services.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponseDTO>> create(@RequestBody RoleRequestDTO requestDTO) {
        ApiResponse<RoleResponseDTO> response = new ApiResponse<>();
        response.setResult(roleService.create(requestDTO));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponseDTO>>> getAllRole() {
        ApiResponse<List<RoleResponseDTO>> response = new ApiResponse<>();
        response.setResult(roleService.getAllRole());
        return ResponseEntity.ok().body(response);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable UUID id) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        response.setResult(roleService.delete(id));
        return ResponseEntity.ok().body(response);
    }

}
