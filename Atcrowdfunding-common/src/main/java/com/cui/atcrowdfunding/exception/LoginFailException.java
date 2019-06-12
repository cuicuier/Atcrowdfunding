package com.cui.atcrowdfunding.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class LoginFailException extends RuntimeException{
    private static final long serialVersionUID=1L;
    public LoginFailException(String message) {
        super(message);
    }
}
