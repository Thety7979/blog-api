package com.tytran.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tytran.blog.dto.request.RoleRequestDTO;
import com.tytran.blog.dto.response.RoleResponseDTO;
import com.tytran.blog.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "permission", ignore = true)
    Role toRole(RoleRequestDTO requestDTO);

    RoleResponseDTO toDTO(Role role);
}
