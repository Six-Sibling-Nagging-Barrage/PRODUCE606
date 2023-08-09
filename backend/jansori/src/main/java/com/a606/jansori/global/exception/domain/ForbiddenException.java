package com.a606.jansori.global.exception.domain;

public class ForbiddenException extends BusinessException {

  private static final String code = "851";
  private static final String message = "인가되지 않은 접근입니다.";

  public ForbiddenException(String code, String message) {
    super(code, message);
  }

  public ForbiddenException() {
    super(code, message);
  }
}
