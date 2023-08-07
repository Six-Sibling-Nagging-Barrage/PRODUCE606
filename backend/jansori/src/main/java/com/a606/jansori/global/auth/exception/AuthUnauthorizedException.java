package com.a606.jansori.global.auth.exception;

import com.a606.jansori.global.exception.UnauthorizedException;

public class AuthUnauthorizedException extends UnauthorizedException {

  private static final String code = "851";
  private static final String message = "인증되지 않은 접근입니다.";

  public AuthUnauthorizedException() {
    super(code, message);
  }
}
