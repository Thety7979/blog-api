package com.tytran.blog.services;

import java.util.List;
import java.util.UUID;

import com.tytran.blog.dto.request.CommentRequestDTO;
import com.tytran.blog.dto.response.CommentResponseDTO;

public interface CommentService {

    CommentResponseDTO create(CommentRequestDTO requestDTO);

    List<CommentResponseDTO> getAllComment();

    Boolean delete(UUID id);
}
