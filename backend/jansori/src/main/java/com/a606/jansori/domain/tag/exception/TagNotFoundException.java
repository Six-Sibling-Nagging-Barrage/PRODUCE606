package com.a606.jansori.domain.tag.exception;

import com.a606.jansori.global.exception.domain.NotFoundException;

public class TagNotFoundException extends NotFoundException {

  private static final String code = "650";
  private static final String message = "해시태그를 찾을 수 없습니다.";

  public TagNotFoundException() {
    super(code, message);
  }
}
