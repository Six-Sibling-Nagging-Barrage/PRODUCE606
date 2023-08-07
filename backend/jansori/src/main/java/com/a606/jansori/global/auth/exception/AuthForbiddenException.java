package com.a606.jansori.global.auth.exception;

import com.a606.jansori.global.exception.ForbiddenException;

public class AuthForbiddenException extends ForbiddenException {

  private static final String code = "850";
  private static final String message = "인가되지 않은 접근입니다.";

  public AuthForbiddenException() {
    super(code, message);
  }

}
