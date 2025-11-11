package com.tytran.blog.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_TRUE(1002, "Password not true", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_NULL(1003, "Password not null", HttpStatus.BAD_REQUEST),
    EMAIL_OR_PASSWORD_NOT_TRUE(1002, "Email or password not true", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1004, "User not found", HttpStatus.NOT_FOUND),
    INVALID_EMAIL_FORMAT(1005, "Invalid email format", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1006, "Password must be at least {min} character", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1007, "Unauthorized: Missing or invalid authentication token", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008, "Permission denied", HttpStatus.FORBIDDEN),
    PERMISSION_NOT_FOUND(1009, "Permission not found", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(1010, "Role not found", HttpStatus.NOT_FOUND),
    ROLE_EXISTS(1011, "Role exists", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTS(1012, "Permission not exists", HttpStatus.BAD_REQUEST),
    INVALID_BIRTHDAY(1013, "You must be at least {min} years old to register", HttpStatus.BAD_REQUEST),
    POST_NOT_FOUND(1014, "Post not found", HttpStatus.NOT_FOUND),
    COMMENT_NOT_FOUND(1015, "Comment not found", HttpStatus.BAD_REQUEST),
    FK_CONSTRAINT_VIOLATION(1016, "Operation failed due to foreign key constraint violation", HttpStatus.CONFLICT)
    ;

    private int code;
    private String message;
    private HttpStatus httpStatus;
}
