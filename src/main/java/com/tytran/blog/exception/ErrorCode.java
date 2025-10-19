package com.tytran.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception"),
    USER_EXISTED(1001, "User existed"),
    PASSWORD_NOT_TRUE(1002, "Password not true"),
    USER_NOT_FOUND(1004, "User not found"),
    INVALID_EMAIL_FORMAT(1005, "Invalid email format"),
    INVALID_PASSWORD(1006, "Invalid password"),
    PASSWORD_NOT_NULL(1003, "Password not null");

    private int code;
    private String message;
}
