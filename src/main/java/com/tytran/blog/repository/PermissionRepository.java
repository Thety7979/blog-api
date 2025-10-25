package com.tytran.blog.repository;

import org.springframework.stereotype.Repository;

import com.tytran.blog.entity.Permission;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    Set<Permission> findAllByNameIn(Set<String> name);
}
