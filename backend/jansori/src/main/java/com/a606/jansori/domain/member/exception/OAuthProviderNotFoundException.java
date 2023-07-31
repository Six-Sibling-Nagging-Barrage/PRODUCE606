package com.a606.jansori.domain.member.exception;

import com.a606.jansori.global.exception.NotFoundException;

public class OAuthProviderNotFoundException extends NotFoundException {

    private static String code = "852";
    public static String message = "서버에서 제공하는 로그인 방식이 아닙니다.";

    public OAuthProviderNotFoundException() {
        super(code, message);
    }
}
