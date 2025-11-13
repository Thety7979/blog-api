package com.tytran.blog.services.implement;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tytran.blog.dto.request.PermissionRequestDTO;
import com.tytran.blog.dto.response.PermissionResponseDTO;
import com.tytran.blog.entity.Permission;
import com.tytran.blog.exception.AppException;
import com.tytran.blog.exception.ErrorCode;
import com.tytran.blog.mapper.PermissionMapper;
import com.tytran.blog.repository.PermissionRepository;
import com.tytran.blog.services.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements PermissionService {

    PermissionRepository permissionRepository;

    PermissionMapper permissionMapper;

    @Override
    public PermissionResponseDTO save(PermissionRequestDTO requestDTO) {
        Permission permission = permissionMapper.createPermission(requestDTO);
        permissionRepository.save(permission);
        return permissionMapper.permissionToDTO(permission);
    }

    @Override
    public List<PermissionResponseDTO> getAllPermission() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::permissionToDTO).toList();
    }

    @Override
    public Boolean delete(UUID id) {
        Permission permission =
                permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        permissionRepository.delete(permission);
        return true;
    }
}
