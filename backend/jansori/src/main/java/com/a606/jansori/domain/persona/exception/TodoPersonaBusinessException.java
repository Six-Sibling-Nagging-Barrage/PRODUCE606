package com.a606.jansori.domain.persona.exception;

import com.a606.jansori.global.exception.domain.BusinessException;

public class TodoPersonaBusinessException extends BusinessException {

  private final static String code = "953";
  private final static String message = "유효하지 않은 투두 캐릭터 정보입니다.";
  public TodoPersonaBusinessException() {
    super(code, message);
  }
}
