package com.a606.jansori.domain.member.exception;

import com.a606.jansori.global.exception.NotFoundException;

public class OAuthProviderNotFoundException extends NotFoundException {

    private static String code = "852";
    public static String message = "올바르지 않은 로그인 방식입니다.";

    public OAuthProviderNotFoundException() {
        super(code, message);
    }
}
