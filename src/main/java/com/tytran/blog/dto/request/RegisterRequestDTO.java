package com.tytran.blog.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class RegisterRequestDTO {
    @Email(message = "INVALID_EMAIL_FORMAT")
    String email;

    @Size(min = 7, message = "INVALID_PASSWORD")
    @NotEmpty(message = "Password must not be null or empty")
    @NotNull(message = "Password must not be null or empty")
    String password;

    @NotEmpty(message = "Fullname must not be null or empty")
    @NotNull(message = "Fullname must not be null or empty")
    String fullname;
}
