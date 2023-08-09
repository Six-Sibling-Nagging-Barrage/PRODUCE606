package com.a606.jansori.domain.notification.exception;

import com.a606.jansori.global.exception.NotFoundException;

public class NotificationNotFoundException extends NotFoundException {

  private static final String code = "900";
  private static final String message = "알림을 찾을 수 없습니다.";

  public NotificationNotFoundException() {
    super(code, message);
  }
}
