package com.a606.jansori.global.auth.exception;

import com.a606.jansori.global.exception.BadRequestException;

public class AuthInvalidAccessTokenException extends BadRequestException {

  private static final String code = "857";
  private static final String message = "유효하지 않은 토큰입니다.";

  public AuthInvalidAccessTokenException() {
    super(code, message);
  }
}
