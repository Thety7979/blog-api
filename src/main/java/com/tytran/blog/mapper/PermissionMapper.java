package com.tytran.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tytran.blog.dto.request.PermissionRequestDTO;
import com.tytran.blog.dto.response.PermissionResponseDTO;
import com.tytran.blog.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    @Mapping(target = "id", ignore = true)
    Permission createPermission(PermissionRequestDTO requestDTO);

    PermissionResponseDTO permissionToDTO(Permission permission);
}
