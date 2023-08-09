package com.a606.jansori.domain.nag.exception;

import com.a606.jansori.global.exception.BadRequestException;

public class NagInvalidRequestException extends BadRequestException  {

  private static final String code = "602";
  private static final String message = "잘못된 잔소리 조회 요청입니다.";

  public NagInvalidRequestException() {
    super(code, message);
  }
}
