package com.a606.jansori.domain.todo.exception;

import com.a606.jansori.global.exception.NotFoundException;

public class TodoNotFoundException extends NotFoundException {

  private static final String code = "700";
  private static final String message = "투두를 찾을 수 없습니다.";

  public TodoNotFoundException() {
    super(code, message);
  }
}
