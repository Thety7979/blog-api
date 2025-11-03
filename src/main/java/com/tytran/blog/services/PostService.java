package com.tytran.blog.services;

import java.util.List;
import java.util.UUID;

import com.tytran.blog.dto.request.PostRequestDTO;
import com.tytran.blog.dto.response.PostResponseDTO;

public interface PostService {
    PostResponseDTO create(PostRequestDTO requestDTO);

    List<PostResponseDTO> getAllPost();

    Boolean delete(UUID id);

    PostResponseDTO update(UUID id, PostRequestDTO requestDTO);

    PostResponseDTO getPostById(UUID id);
}
