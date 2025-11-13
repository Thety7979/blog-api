package com.tytran.blog.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tytran.blog.dto.ApiResponse;
import com.tytran.blog.dto.request.CommentRequestDTO;
import com.tytran.blog.dto.response.CommentResponseDTO;
import com.tytran.blog.services.CommentService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {

    CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDTO>> create(@RequestBody CommentRequestDTO requestDTO) {
        ApiResponse<CommentResponseDTO> response = new ApiResponse<>();
        response.setMessage("Tạo comment thành công");
        response.setResult(commentService.create(requestDTO));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<CommentResponseDTO>>> getAllComment() {
        ApiResponse<List<CommentResponseDTO>> response = new ApiResponse<>();
        response.setResult(commentService.getAllComment());
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable UUID id) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        response.setResult(commentService.delete(id));
        return ResponseEntity.ok().body(response);
    }
}
