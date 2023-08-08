package com.a606.jansori.global.auth.exception;

import com.a606.jansori.global.exception.BadRequestException;

public class AuthInvalidPasswordException extends BadRequestException {

	private static final String code = "858";
	private static final String message = "비밀번호가 유효하지 않습니다.";

	public AuthInvalidPasswordException() {
		super(code, message);
	}
}
