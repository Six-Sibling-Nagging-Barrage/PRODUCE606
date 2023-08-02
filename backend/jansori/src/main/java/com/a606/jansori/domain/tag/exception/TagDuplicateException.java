package com.a606.jansori.domain.tag.exception;

import com.a606.jansori.global.exception.BusinessException;

public class TagDuplicateException extends BusinessException  {
  private static final String code = "651";
  private static final String message = "중복되는 해시태그입니다.";

  public TagDuplicateException() {
    super(code, message);
  }
}
