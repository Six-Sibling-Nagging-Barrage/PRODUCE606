package com.a606.jansori.domain.todo.exception;

import com.a606.jansori.global.exception.domain.BusinessException;

public class TodoBusinessException extends BusinessException {

  private static final String code = "702";
  private static final String message = "투두의 태그가 올바르지 않습니다.";

  public TodoBusinessException() {
    super(code, message);
  }
}
