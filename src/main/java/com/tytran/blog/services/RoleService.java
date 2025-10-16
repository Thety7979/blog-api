package com.tytran.blog.services;

import java.util.UUID;

import com.tytran.blog.entity.Role;

public interface RoleService {
    Role findById(UUID id);
    Role findByRoleWithUserId(UUID id);
}
