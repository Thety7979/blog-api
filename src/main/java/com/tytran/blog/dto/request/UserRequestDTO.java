package com.tytran.blog.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private UUID id;
    private String email;
    private String fullname;
    private String currentPassword;
    private String newPassword;
}
