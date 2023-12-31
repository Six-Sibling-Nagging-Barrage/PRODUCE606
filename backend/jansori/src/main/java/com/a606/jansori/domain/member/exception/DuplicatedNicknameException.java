package com.a606.jansori.domain.member.exception;

import com.a606.jansori.global.exception.domain.BadRequestException;
import lombok.Getter;

public class DuplicatedNicknameException extends BadRequestException {

  private static final String code = "801";
  private static final String message = "중복되는 닉네임이 있습니다.";

  public DuplicatedNicknameException() {
    super(code, message);
  }
}
