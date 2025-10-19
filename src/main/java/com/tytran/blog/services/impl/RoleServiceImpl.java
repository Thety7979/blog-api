package com.tytran.blog.services.impl;

import java.util.UUID;
import org.springframework.stereotype.Service;

import com.tytran.blog.entity.Role;
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

    @Override
    public Role findById(UUID id) {
        return roleDAO.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Override
    public Role findByRoleWithUserId(UUID id) {
        return roleDAO.findByRoleWithUserId(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy role cho user có id: " + id));
    }

}
