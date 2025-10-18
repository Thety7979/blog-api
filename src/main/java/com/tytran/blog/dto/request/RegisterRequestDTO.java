package com.tytran.blog.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDTO {
    @Email
    private String email;

    @Size(min = 8, message = "Password must be at least 8 character")
    @NotEmpty(message = "Password must not be null or empty")
    @NotNull(message = "Password must not be null or empty")
    private String password;

    @NotEmpty(message = "Fullname must not be null or empty")
    @NotNull(message = "Fullname must not be null or empty")
    private String fullname;
    
    private UUID roleId;
}
