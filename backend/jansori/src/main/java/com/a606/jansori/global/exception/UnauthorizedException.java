package com.a606.jansori.global.exception;

import com.a606.jansori.global.exception.BadRequestException;

public class UnauthorizedException extends BadRequestException {
    public UnauthorizedException(String code, String message) {
        super(code, message);
    }
}
