package com.a606.jansori.global.exception;

public class ForbiddenException extends BusinessException {
    public ForbiddenException(String code, String message) {
        super(code, message);
    }
}
