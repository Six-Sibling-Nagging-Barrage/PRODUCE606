package com.a606.jansori.domain.persona.exception;

import com.a606.jansori.global.exception.domain.NotFoundException;

public class TodoPersonaNotFoundException extends NotFoundException {

  private final static String code = "952";
  private final static String message = "투두에 대한 캐릭터를 찾을 수 없습니다.";
  public TodoPersonaNotFoundException() {
    super(code, message);
  }
}
