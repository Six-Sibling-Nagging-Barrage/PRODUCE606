package com.a606.jansori.domain.member.exception;

import com.a606.jansori.global.exception.domain.BadRequestException;

public class DuplicatedEmailException extends BadRequestException {

  private static final String code = "853";
  private static final String message = "이미 가입된 계정입니다.";

  public DuplicatedEmailException() {
    super(code, message);
  }
}
