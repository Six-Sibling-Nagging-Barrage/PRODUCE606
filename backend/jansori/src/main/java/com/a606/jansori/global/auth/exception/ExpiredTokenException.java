package com.a606.jansori.global.auth.exception;

import com.a606.jansori.global.exception.domain.UnauthorizedException;

public class ExpiredTokenException extends UnauthorizedException {

  private static final String code = "852";
  private static final String message = "만료된 토큰입니다.";

  public ExpiredTokenException() {
    super(code, message);
  }
}
