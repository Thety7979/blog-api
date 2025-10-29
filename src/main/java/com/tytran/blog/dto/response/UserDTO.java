package com.tytran.blog.dto.response;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    UUID id;
    String email;
    String fullname;
    LocalDate birthday;
    Set<RoleResponseDTO> roles;
}
