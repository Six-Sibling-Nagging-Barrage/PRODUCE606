package com.a606.jansori.infra.storage.exception;

import com.a606.jansori.global.exception.BadRequestException;
import lombok.Getter;

@Getter
public class FileUploadException extends BadRequestException {

  private static final String code = "804";
  private static final String message = "업로드 과정에서 문제가 발생했습니다.";

  public FileUploadException() {
    super(code, message);
  }
}
