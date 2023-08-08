package com.a606.jansori.infra.storage.exception;

import com.a606.jansori.global.exception.BadRequestException;
import lombok.Getter;

@Getter
public class FileConversionException extends BadRequestException {

  private static final String code = "805";
  private static final String message = "파일 변환에 오류가 생겼습니다.";

  public FileConversionException() {
    super(code, message);
  }
}
