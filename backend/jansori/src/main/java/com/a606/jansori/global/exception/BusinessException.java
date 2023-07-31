package com.a606.jansori.global.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private String code;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }
}
