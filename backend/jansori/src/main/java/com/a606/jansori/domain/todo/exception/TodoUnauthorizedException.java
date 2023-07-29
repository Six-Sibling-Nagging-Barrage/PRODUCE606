package com.a606.jansori.domain.todo.exception;

import com.a606.jansori.global.exception.UnauthorizedException;

public class TodoUnauthorizedException extends UnauthorizedException {

  private static final String code = "701";
  private static final String message = "허용되지 않는 투두에 대한 요청입니다.";

  public TodoUnauthorizedException() {
    super(code, message);
  }
}
