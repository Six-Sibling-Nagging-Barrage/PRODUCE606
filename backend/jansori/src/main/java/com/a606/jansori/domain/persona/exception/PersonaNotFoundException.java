package com.a606.jansori.domain.persona.exception;

import com.a606.jansori.global.exception.NotFoundException;

public class PersonaNotFoundException extends NotFoundException {

  private final static String code = "950";

  private final static String message = "페르소나를 찾을 수 없습니다.";

  public PersonaNotFoundException() {
    super(code, message);
  }
}
