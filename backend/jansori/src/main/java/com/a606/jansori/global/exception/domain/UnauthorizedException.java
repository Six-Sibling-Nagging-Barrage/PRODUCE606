package com.a606.jansori.global.exception.domain;

public class UnauthorizedException extends BadRequestException {

  private static final String code = "850";
  private static final String message = "인증에 실패했습니다.";

  public UnauthorizedException(String code, String message) {
    super(code, message);
  }

  public UnauthorizedException() {
    super(code, message);
  }

}
