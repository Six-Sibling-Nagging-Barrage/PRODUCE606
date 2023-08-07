package com.a606.jansori.global.auth.exception;

import com.a606.jansori.global.exception.BadRequestException;

public class AuthMemberNotFoundException extends BadRequestException {

  private static final String code = "854";
  private static final String message = "해당 유저가 존재하지 않습니다.";

  public AuthMemberNotFoundException() {
    super(code, message);
  }
}
