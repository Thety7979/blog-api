package com.tytran.blog.dto.response;

import java.util.Set;
import java.util.UUID;

import com.tytran.blog.entity.Permission;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponseDTO {
    UUID id;
    String name;
    String description;
    Set<Permission> permission;
}
