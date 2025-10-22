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
    USER_NOT_FOUND(1004, "User not found", HttpStatus.NOT_FOUND),
    INVALID_EMAIL_FORMAT(1005, "Invalid email format", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1006, "Invalid password", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1007, "Unauthorization", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008, "Unauthorized", HttpStatus.FORBIDDEN);

    private int code;
    private String message;
    private HttpStatus httpStatus;
}
