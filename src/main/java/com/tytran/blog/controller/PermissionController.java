package com.tytran.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tytran.blog.dto.ApiResponse;
import com.tytran.blog.dto.request.PermissionRequestDTO;
import com.tytran.blog.dto.response.PermissionResponseDTO;
import com.tytran.blog.services.PermissionService;

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
@RequestMapping("/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {

    PermissionService permissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<PermissionResponseDTO>> save(@RequestBody PermissionRequestDTO requestDTO) {
        ApiResponse<PermissionResponseDTO> response = new ApiResponse<>();
        response.setResult(permissionService.save(requestDTO));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PermissionResponseDTO>>> getPermission() {
        ApiResponse<List<PermissionResponseDTO>> response = new ApiResponse<>();
        response.setResult(permissionService.getAllPermission());
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable UUID id){
        ApiResponse<Boolean> response = new ApiResponse<>();
        response.setResult(permissionService.delete(id));
        return ResponseEntity.ok().body(response);
    }
    

}
