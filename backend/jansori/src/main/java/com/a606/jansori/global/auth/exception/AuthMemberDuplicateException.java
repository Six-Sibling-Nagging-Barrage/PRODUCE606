package com.a606.jansori.global.auth.exception;

import com.a606.jansori.global.exception.BadRequestException;

public class AuthMemberDuplicateException extends BadRequestException {

  private static final String code = "853";
  private static final String message = "이미 가입된 계정입니다.";

  public AuthMemberDuplicateException() {
    super(code, message);
  }
}
