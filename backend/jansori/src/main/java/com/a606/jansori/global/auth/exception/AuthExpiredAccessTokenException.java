package com.a606.jansori.global.auth.exception;

import com.a606.jansori.global.exception.BadRequestException;

public class AuthExpiredAccessTokenException extends BadRequestException {

  private static final String code = "856";
  private static final String message = "만료된 토큰입니다.";

  public AuthExpiredAccessTokenException() {
    super(code, message);
  }
}
