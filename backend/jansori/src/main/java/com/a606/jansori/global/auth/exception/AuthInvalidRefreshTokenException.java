package com.a606.jansori.global.auth.exception;

import com.a606.jansori.global.exception.BadRequestException;

public class AuthInvalidRefreshTokenException extends BadRequestException {

  private static final String code = "855";
  private static final String message = "유효한 토큰이 아닙니다.";

  public AuthInvalidRefreshTokenException() {
    super(code, message);
  }
}
