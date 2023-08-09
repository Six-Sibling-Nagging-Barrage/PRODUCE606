package com.a606.jansori.global.auth.exception;

import com.a606.jansori.global.exception.domain.BadRequestException;

public class InvalidTokenException extends BadRequestException {

  private static final String code = "853";
  private static final String message = "유효하지 않은 토큰입니다.";

  public InvalidTokenException() {
    super(code, message);
  }
}
