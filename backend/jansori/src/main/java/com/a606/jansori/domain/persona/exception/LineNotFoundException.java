package com.a606.jansori.domain.persona.exception;

import com.a606.jansori.global.exception.NotFoundException;

public class LineNotFoundException extends NotFoundException {

  private static final String code = "950";
  private static final String message = "페르소나의 잔소리 대사를 찾을 수 없습니다.";
  public LineNotFoundException() {
    super(code, message);
  }
}
