package com.tytran.blog.repository;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tytran.blog.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    Set<Permission> findAllByNameIn(Set<String> name);
}
