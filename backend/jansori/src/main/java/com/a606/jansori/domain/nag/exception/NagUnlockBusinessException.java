package com.a606.jansori.domain.nag.exception;

import com.a606.jansori.global.exception.BusinessException;

public class NagUnlockBusinessException extends BusinessException {

  private static final String code = "601";
  private static final String message = "잔소리의 초성 해제를 할 수 없습니다.";
  public NagUnlockBusinessException() {
    super(code, message);
  }
}
