package com.tytran.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tytran.blog.dto.ApiResponse;
import com.tytran.blog.dto.request.CommentRequestDTO;
import com.tytran.blog.dto.response.CommentResponseDTO;
import com.tytran.blog.services.CommentService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

}
