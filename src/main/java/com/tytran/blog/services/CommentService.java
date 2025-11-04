package com.tytran.blog.services;

import com.tytran.blog.dto.request.CommentRequestDTO;
import com.tytran.blog.dto.response.CommentResponseDTO;

public interface CommentService {
    
    CommentResponseDTO create(CommentRequestDTO requestDTO);
}
