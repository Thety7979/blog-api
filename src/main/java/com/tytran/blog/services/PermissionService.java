package com.tytran.blog.services;

import java.util.List;
import java.util.UUID;

import com.tytran.blog.dto.request.PermissionRequestDTO;
import com.tytran.blog.dto.response.PermissionResponseDTO;

public interface PermissionService {
    PermissionResponseDTO save(PermissionRequestDTO requestDTO);
    List<PermissionResponseDTO> getAllPermission();
    Boolean delete(UUID id);
}
