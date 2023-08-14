package com.a606.jansori.domain.notification.exception;

import com.a606.jansori.global.exception.domain.NotFoundException;

public class NotificationSettingNotFoundException extends NotFoundException {

  private static final String code = "901";
  private static final String message = "알림설정 정보를 찾을 수 없습니다.";

  public NotificationSettingNotFoundException() {
    super(code, message);
  }
}
