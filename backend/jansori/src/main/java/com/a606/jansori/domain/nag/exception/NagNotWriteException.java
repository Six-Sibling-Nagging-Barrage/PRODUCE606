package com.a606.jansori.domain.nag.exception;

import com.a606.jansori.global.exception.BadRequestException;

public class NagNotWriteException extends BadRequestException {
    public NagNotWriteException(String code, String message) {
        super(code, message);
    }
}
