package com.a606.jansori.domain.persona.exception;

import com.a606.jansori.global.exception.domain.ForbiddenException;

public class ReactionForbiddenException extends ForbiddenException {

  private static final String code = "951";

  private static final String message = "이미 좋아요한 캐릭터입니다.";

  public ReactionForbiddenException() {
    super(code, message);
  }
}
