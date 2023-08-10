package com.a606.jansori.infra.message.exception;

import com.a606.jansori.global.exception.domain.InternalException;

public class JsonParsingException extends InternalException {

  private static final String code = "860";
  private static final String message = "Json 처리 과정에 문제가 생겼습니다.";


  public JsonParsingException() {
    super(code, message);
  }
}
