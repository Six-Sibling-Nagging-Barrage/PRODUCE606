package com.a606.jansori.domain.nag.exception;

import com.a606.jansori.global.exception.domain.BusinessException;

public class NagLikeBusinessException extends BusinessException {

  private static final String code = "603";
  private static final String message = "잔소리의 초성 해제를 먼저 해야 합니다.";
  public NagLikeBusinessException() {
    super(code, message);
  }
}
