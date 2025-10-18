package com.tytran.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception"),
    USER_EXISTED(1001, "User existed"),
    PASSWORD_NOT_TRUE(1002, "Password not true"),
    PASSWORD_NOT_NULL(1003, "Password not null");

    private int code;
    private String message;
}
