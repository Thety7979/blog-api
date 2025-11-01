package com.tytran.blog.controller;

import org.springframework.web.bind.annotation.RestController;

import com.tytran.blog.dto.ApiResponse;
import com.tytran.blog.dto.request.PostRequestDTO;
import com.tytran.blog.dto.response.PostResponseDTO;
import com.tytran.blog.services.PostService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/post")
public class PostController {

    PostService postService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PostResponseDTO>> create(@RequestBody PostRequestDTO requestDTO) {
        ApiResponse<PostResponseDTO> response = new ApiResponse<>();
        response.setResult(postService.create(requestDTO));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<PostResponseDTO>>> getAllPost() {
        ApiResponse<List<PostResponseDTO>> response = new ApiResponse<>();
        response.setResult(postService.getAllPost());
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable UUID id) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        response.setResult(postService.delete(id));
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PostResponseDTO>> update(@PathVariable UUID id, @RequestBody PostRequestDTO requestDTO) {
        ApiResponse<PostResponseDTO> response = new ApiResponse<>();
        response.setResult(postService.update(id, requestDTO));
        return ResponseEntity.ok().body(response);
    }

}
