package com.tytran.blog.services.implement;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.tytran.blog.dto.request.RoleRequestDTO;
import com.tytran.blog.dto.response.RoleResponseDTO;
import com.tytran.blog.entity.Permission;
import com.tytran.blog.entity.Role;
import com.tytran.blog.exception.AppException;
import com.tytran.blog.exception.ErrorCode;
import com.tytran.blog.mapper.RoleMapper;
import com.tytran.blog.repository.PermissionRepository;
import com.tytran.blog.repository.RoleRepository;
import com.tytran.blog.services.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {

    RoleRepository roleDAO;

    RoleMapper roleMapper;

    PermissionRepository permissionRepository;

    @Override
    public RoleResponseDTO create(RoleRequestDTO requestDTO) {
        Role role = roleMapper.toRole(requestDTO);
        Set<Permission> permission = permissionRepository.findAllByNameIn(requestDTO.getPermissions());
        if (permission.isEmpty()) {
            throw new AppException(ErrorCode.PERMISSION_NOT_EXISTS);
        }
        role.setPermission(permission);
        role = roleDAO.save(role);
        return roleMapper.toDTO(role);
    }

    @Override
    public List<RoleResponseDTO> getAllRole() {
        return roleDAO.findAll().stream().map(roleMapper::toDTO).toList();
    }

    @Override
    public Boolean delete(UUID id) {
        Role role = roleDAO.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        roleDAO.delete(role);
        return true;
    }

}
