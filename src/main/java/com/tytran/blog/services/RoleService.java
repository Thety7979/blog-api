package com.tytran.blog.services;

import java.util.List;
import java.util.UUID;

import com.tytran.blog.dto.request.RoleRequestDTO;
import com.tytran.blog.dto.response.RoleResponseDTO;

public interface RoleService {
    RoleResponseDTO create(RoleRequestDTO requestDTO);

    List<RoleResponseDTO> getAllRole();

    Boolean delete(UUID id);

    RoleResponseDTO update(UUID id, RoleRequestDTO requestDTO);
}
