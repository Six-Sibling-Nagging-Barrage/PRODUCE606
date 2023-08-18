package com.a606.jansori.domain.nag.exception;

import com.a606.jansori.global.exception.domain.NotFoundException;

public class NagNotFoundException extends NotFoundException {

  private static final String code = "600";
  private static final String message = "잔소리를 찾을 수 없습니다.";

  public NagNotFoundException() {
    super(code, message);
  }
}
